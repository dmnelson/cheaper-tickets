package mn.david.cheapertickets

/**
 * User: David
 * Date: 16/07/11
 * Time: 20:22
 */
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

import groovyx.net.http.Method
import groovyx.net.http.HttpResponseDecorator

import org.apache.http.impl.cookie.BasicClientCookie
import mn.david.cheapertickets.request.SearchRequest
import mn.david.cheapertickets.request.StatusRequest

@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.1' )
def http = new HTTPBuilder('http://www.submarinoviagens.com.br/Passagens/UIService/Service.svc/')

def request = new SearchRequest();
request.origin.with {
    code = 'POA'
    departureDay = Date.parse("dd/MM/yyyy", "23/09/2011");
}
request.destination.with {
    code = 'BHZ'
    departureDay = Date.parse("dd/MM/yyyy", "25/09/2011");
}
def  out = new File('out.txt').newWriter();
http.post(path: 'SearchGroupedFlights', body: request.toString(), requestContentType: ContentType.JSON) { resp, json ->
    out << "SearchGroupedFlights - ${json}"
    out.newLine();
    if (!json.Errors && json.PullStatusFrom != null) {
        def status = json.Status;
        def pullStatusFrom = new URL(json.PullStatusFrom);
        def searchId = json.SearchId;
        while (status == 0) {
            Thread.sleep(5000);
            def statusRequest = new StatusRequest(pullStatusFrom: pullStatusFrom, searchId: searchId);
            http.request(Method.POST, ContentType.JSON) {  req ->
                uri.path = 'GetSearchStatus';
                body = statusRequest.toString();
                response.failure = { HttpResponseDecorator rs ->
                    status = 1;
                    System.out << rs.entity.content;
                }

                response.success = {statusResp, statusJson ->
                    status = statusJson.Status;
                    pullStatusFrom = new URL(statusJson.PullStatusFrom);
                    searchId = statusJson.SearchId;
                    if(statusJson.Errors){
                        status = 1;
                        println statusJson.Errors
                    }
                    out << "GetSearchStatus - ${statusJson}"
                    out.newLine();
                }
            }
        }
    }
}
out.close();