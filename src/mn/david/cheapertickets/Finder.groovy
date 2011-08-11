package mn.david.cheapertickets

import mn.david.cheapertickets.search.engine.submarino.builder.SearchRequestBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.ContentType
import mn.david.cheapertickets.search.engine.submarino.request.StatusRequest
import groovyx.net.http.Method

import mn.david.cheapertickets.util.DelegateScopeClosure
import mn.david.cheapertickets.search.SearchQuery

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 7/29/11
 * Time: 12:21 PM
 * tickets {*     going from POA to BHZ on 'dd/MM/yyyy' returning on 'dd/MM/yyyy'}airfares{tickets(going).from(POA).to(BHZ).on('dd/MM/yyyy');}*/

class Finder {

    def airfares(Closure closure) {
        def query = new SearchQuery();
        def closureDelegate = new DelegateScopeClosure(closure);
        closureDelegate.call(query);
        return results(query);
    }

    protected def results(SearchQuery query) {
        def request = SearchRequestBuilder.create {
            from query.origin
            to query.destination
            at query.departureDate
        }.toJSONString();

        def http = new HTTPBuilder('http://www.submarinoviagens.com.br/Passagens/UIService/Service.svc/')
        http.post(path: 'SearchGroupedFlights', body: request, requestContentType: ContentType.JSON) { resp, json ->
            if (!json.Errors && json.PullStatusFrom != null) {
                def status = json.Status;
                def pullStatusFrom = new URL(json.PullStatusFrom);
                def searchId = json.SearchId;
                while (status == 0) {
                    Thread.sleep(1000);
                    def statusRequest = new StatusRequest(pullStatusFrom: pullStatusFrom, searchId: searchId);
                    http.request(Method.POST, ContentType.JSON) {  req ->
                        uri.path = 'GetSearchStatus';
                        body = statusRequest.toString();
                        response.failure = { HttpResponseDecorator rs ->
                            status = 1;
                        }

                        response.success = {statusResp, statusJson ->
                            status = statusJson.Status;
                            pullStatusFrom = new URL(statusJson.PullStatusFrom);
                            searchId = statusJson.SearchId;
                            if (statusJson.Errors) {
                                status = 1;
                                println statusJson.Errors
                            }
                        }
                    }
                }
            }
        }
    }

}
