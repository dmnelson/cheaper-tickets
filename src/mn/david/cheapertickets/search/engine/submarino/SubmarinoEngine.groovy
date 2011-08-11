package mn.david.cheapertickets.search.engine.submarino

import mn.david.cheapertickets.search.engine.Engine
import mn.david.cheapertickets.search.engine.Searcher
import mn.david.cheapertickets.search.SearchQuery

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:02 PM
 */
class SubmarinoEngine implements Engine {

    SubmarinoSearcher searcherFor(SearchQuery searchData) {
        new SubmarinoSearcher(searchData);
    }

}
