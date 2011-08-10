package mn.david.cheapertickets

import mn.david.cheapertickets.search.engine.submarino.builder.SearchRequestBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.ContentType
import mn.david.cheapertickets.search.request.StatusRequest
import groovyx.net.http.Method

import mn.david.cheapertickets.domain.City
import mn.david.cheapertickets.util.Configuration

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 7/29/11
 * Time: 12:21 PM
 * tickets {*     going from POA to BHZ on 'dd/MM/yyyy' returning on 'dd/MM/yyyy'}airfares{tickets(going).from(POA).to(BHZ).on('dd/MM/yyyy');}*/

class Finder {

    def airfares(Closure closure) {
        def query = new SearchQuery();
        closure.delegate = query;
        closure.call();
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


    protected static class SearchQuery {
        City origin;
        City destination;
        Date departureDate;
        String dateFormat = Configuration.get{ cheaperTickets.dateFormat }

        static {
            SearchQuery.metaClass.getProperty = { name ->
                def metaProperty = SearchQuery.metaClass.getMetaProperty(name)
                metaProperty ? metaProperty.getProperty(delegate) : City.getCity(name);
            }
        }

        def from(City city) {
            origin = city;
            return this;
        }

        def to(City city) {
            destination = city;
            return this;
        }

        def from(String city) {
            from getCity(city);
        }

        def to(String city) {
            to getCity(city);
        }

        def at(String date) {
            departureDate = Date.parse(dateFormat, date);
            return this
        }

        private static City getCity(String nameOrCode) {
            def aCity = City.getCity(nameOrCode);
            if (!aCity) {
                throw new IllegalArgumentException("Invalid city, must be existent. Check 'City.values()'")
            }
            return aCity;
        }

    }
}
