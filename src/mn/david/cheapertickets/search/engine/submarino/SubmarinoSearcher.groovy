package mn.david.cheapertickets.search.engine.submarino

import groovy.transform.InheritConstructors
import groovy.util.logging.Commons
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import mn.david.cheapertickets.configuration.Configuration
import mn.david.cheapertickets.search.SearchException
import mn.david.cheapertickets.search.engine.AbstractSearcher
import mn.david.cheapertickets.search.engine.submarino.request.SearchRequest
import mn.david.cheapertickets.search.engine.submarino.request.StatusRequest
import mn.david.cheapertickets.search.engine.submarino.response.Response
import mn.david.cheapertickets.search.engine.submarino.response.SearchResponse
import mn.david.cheapertickets.search.engine.submarino.response.StatusResponse

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:10 PM
 */
@Commons
@InheritConstructors
class SubmarinoSearcher extends AbstractSearcher {

    private String searchId;
    private URL pullStatusFrom;

    boolean requestSearch() {
        reset()

        this.results = new TreeSet();

        httpRequest(path: webserviceConfig.search, parameters: new SearchRequest(searchQuery).toJSONString()) { response, json ->
            def searchResponse = new SearchResponse(json);
            updateData(searchResponse);
        }

        return pullStatusFrom && searchId;
    }

    void updateResults() {
        def statusRequest = new StatusRequest(searchId, pullStatusFrom);
        httpRequest path: webserviceConfig.status, parameters: statusRequest.toJSONString(), { response, json ->
            def statusResponse = new StatusResponse(json);
            println '<>'
            println results.size()
            println statusResponse.tickets.size()
            results.addAll statusResponse.tickets;
            println results.size()
            println '</>'
            updateData(statusResponse);
        }
    }


    private void httpRequest(data, Closure onSuccess) {
        def http = new HTTPBuilder(webserviceConfig.baseURL)
        http.request(Method.POST, ContentType.JSON) {  req ->
            uri.path = data.path;
            body = data.parameters?.toString();

            response.failure = data.onFailure ?: {  rs ->
                hasErrors = true;
            }

            response.success = onSuccess;
        }
    }

    private void updateData(Response response) {
        if (response.errors) {
            throw new SearchException(errors: response.errors);
        }

        this.isComplete = response.isCompleted();
        this.pullStatusFrom = response.pullStatusFrom;
        this.searchId = response.searchId;
    }

    private def getWebserviceConfig() {
        Configuration.get { cheaperTickets.engine.submarino.webservice };
    }
}
