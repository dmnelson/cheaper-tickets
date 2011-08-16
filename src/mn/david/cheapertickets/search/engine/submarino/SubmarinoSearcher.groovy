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
import mn.david.cheapertickets.search.engine.submarino.response.SearchResponse
import mn.david.cheapertickets.search.engine.submarino.SubmarinoEngine.Status
import mn.david.cheapertickets.search.engine.submarino.response.Response
import mn.david.cheapertickets.search.engine.submarino.response.StatusResponse

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:10 PM
 */
@InheritConstructors
class SubmarinoSearcher extends AbstractSearcher {

    private String searchId;
    private URL pullStatusFrom;

    boolean requestSearch() {
        reset()
        httpRequest(path: webserviceConfig.search, parameters: new SearchRequest(searchQuery)) { response, json ->
            def searchResponse = new SearchResponse(json);
            updateData(searchResponse);
        }
        return pullStatusFrom && searchId;
    }

    void updateResults() {
        if (!searchId)
            throw new IllegalStateException("SearchId must be NOT null. Call 'requestSearch' before.")

        if (!pullStatusFrom)
            throw new IllegalStateException("PullStatusFrom must be NOT null.")

        def statusRequest = new StatusRequest(pullStatusFrom: pullStatusFrom, searchId: searchId);
        httpRequest path: webserviceConfig.status, parameters: statusRequest.toString(), { response, json ->
            def statusResponse = new StatusResponse(json);
            updateData(statusResponse);
        }

    }


    private void httpRequest(data, Closure onSucess) {
        def http = new HTTPBuilder(webserviceConfig.baseURL)
        http.request(Method.POST, ContentType.JSON) {  req ->
            uri.path = data.path;
            body = data.parameters?.toString();

            response.failure = data.onFailure ?: {  rs ->
                completed();
            }

            response.success = onSucess;
        }
    }

    private void updateData(Response response) {
        if (response.errors) {
            throw new SearchException(errors: response.errors);
        }
        if (response.status == Status.COMPLETE) {
            completed();
        }
        this.pullStatusFrom = response.pullStatusFrom;
        this.searchId = response.searchId;
    }

    private static getWebserviceConfig() {
        Configuration.get { cheaperTickets.engine.submarino.webservice };
    }
}
