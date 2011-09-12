package mn.david.cheapertickets

import mn.david.cheapertickets.search.SearchQuery
import mn.david.cheapertickets.search.engine.Engine
import mn.david.cheapertickets.search.engine.EngineFactory
import mn.david.cheapertickets.util.DelegateScopeClosure

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 7/29/11
 * Time: 12:21 PM
 */

class Finder {

    private Engine engine;

    Finder() {
        def engineFactory = new EngineFactory();
        if (!engineFactory.defaultEngine) {
            throw new IllegalArgumentException('Default engine is null')
        }
        engine = engineFactory.defaultEngine;
    }

    Finder(Engine engine) {
        if (!engine) {
            throw new IllegalArgumentException('Engine must not be null.')
        }
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
