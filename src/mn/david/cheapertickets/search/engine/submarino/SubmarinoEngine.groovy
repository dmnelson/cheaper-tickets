package mn.david.cheapertickets.search.engine.submarino

import mn.david.cheapertickets.domain.Ticket
import mn.david.cheapertickets.search.SearchQuery
import mn.david.cheapertickets.search.engine.Engine

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:02 PM
 */
class SubmarinoEngine implements Engine {

    SubmarinoSearcher searcherFor(SearchQuery searchData) {
        new SubmarinoSearcher(searchData);
    }

    Collection<Ticket> search(SearchQuery searchQuery) {
        searcherFor(searchQuery).search();
    }

}
