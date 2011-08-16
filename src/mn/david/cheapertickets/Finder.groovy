package mn.david.cheapertickets

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.ContentType
import mn.david.cheapertickets.search.engine.submarino.request.StatusRequest
import groovyx.net.http.Method

import mn.david.cheapertickets.util.DelegateScopeClosure
import mn.david.cheapertickets.search.SearchQuery
import mn.david.cheapertickets.search.engine.Engine
import mn.david.cheapertickets.configuration.Configuration

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
        Engine engine = Configuration.engine;
        engine.search(query);
    }

}
