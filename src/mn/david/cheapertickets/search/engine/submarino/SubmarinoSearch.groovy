package mn.david.cheapertickets.search.engine.submarino

import mn.david.cheapertickets.search.AbstractSearch
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import mn.david.cheapertickets.search.request.Request
import mn.david.cheapertickets.search.request.SearchRequest
import mn.david.cheapertickets.search.SearchException
import mn.david.cheapertickets.util.Configuration

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:10 PM
 */
class SubmarinoSearch extends AbstractSearch {

    private String searchId;
    private String pullStatusFrom;
    private SearchRequest searchRequest;

    void requestSearch(SearchRequest request) {
        def webserviceConfig = Configuration.get().cheaperTickets.engine.submarino.webservice;
        def http = new HTTPBuilder(webserviceConfig.baseURL)
        http.post(path: webserviceConfig.search, body: request, requestContentType: ContentType.JSON) { response, json ->
            println json;
            if (json.Errors) {
                throw new SearchException(json.Errors.toString());
            }
            this.pullStatusFrom = json.PullStatusFrom;
            this.searchId = json.SearchId;
            if (json.Status != 0) {
                completed();
            }
        }
    }

    void updateResults() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    List getResults() {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }
}
