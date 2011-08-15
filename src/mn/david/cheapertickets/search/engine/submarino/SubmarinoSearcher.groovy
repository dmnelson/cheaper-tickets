package mn.david.cheapertickets.search.engine.submarino

import mn.david.cheapertickets.search.engine.AbstractSearcher
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

import mn.david.cheapertickets.search.engine.submarino.request.SearchRequest
import mn.david.cheapertickets.search.SearchException
import mn.david.cheapertickets.configuration.Configuration
import groovy.transform.InheritConstructors
import mn.david.cheapertickets.search.engine.submarino.request.StatusRequest
import groovyx.net.http.Method

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:10 PM
 */
@InheritConstructors
class SubmarinoSearcher extends AbstractSearcher {

    private String searchId;
    private URL pullStatusFrom;
    private SearchRequest searchRequest;

    boolean requestSearch() {
        reset()
        httpRequest(path: webserviceConfig.search, parameters: searchRequest.toString()) { response, json ->
            println json;
            if (json.Errors) {
                throw new SearchException(json.Errors.toString());
            }
            this.pullStatusFrom = new URL(json.PullStatusFrom);
            this.searchId = json.SearchId;
            if (json.Status != 0) {
                completed();
            }
        }
        return true;
    }

    void updateResults() {
        if (!searchId)
            throw new IllegalStateException("SearchId must be NOT null. Call 'requestSearch' before.")

        if (!pullStatusFrom)
            throw new IllegalStateException("PullStatusFrom must be NOT null.")

        def statusRequest = new StatusRequest(pullStatusFrom: pullStatusFrom, searchId: searchId);
        httpRequest path: webserviceConfig.status, parameters: statusRequest.toString(), { response, json ->
            if (json.Status == 1) {
                completed();
            }
            pullStatusFrom = new URL(json.PullStatusFrom);
            searchId = json.SearchId;
            if (json.Errors) {
                completed();
                println json.Errors
            }
        }

    }


    private void httpRequest(data, Closure onSucess) {
        def http = new HTTPBuilder(webserviceConfig.baseURL)
        http.request(Method.POST, ContentType.JSON) {  req ->
            uri.path = data.path;
            body = data.parameters;

            response.failure = data.onFailure ?: {  rs ->
                completed();
            }

            response.success = onSucess;
        }
    }

    private static getWebserviceConfig() {
        Configuration.get { cheaperTickets.engine.submarino.webservice };
    }
}
