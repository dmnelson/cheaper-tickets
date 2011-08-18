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
import mn.david.cheapertickets.util.EngineFactory

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 7/29/11
 * Time: 12:21 PM
 * tickets {*     going from POA to BHZ on 'dd/MM/yyyy' returning on 'dd/MM/yyyy'}airfares{tickets(going).from(POA).to(BHZ).on('dd/MM/yyyy');}*/

class Finder {

    private Engine engine;

    Finder() {
        engine = EngineFactory.defaultEngine;
    }

    Finder(Engine engine) {
        this.engine = engine;
    }

    def airfares(Closure closure) {
        def query = buildQuery(closure);
        return engine.search(query);
    }

    private buildQuery(Closure closure) {
        new SearchQuery().with {
            def closureDelegate = new DelegateScopeClosure(closure);
            closureDelegate.call(it);
            return it;
        }
    }

}
