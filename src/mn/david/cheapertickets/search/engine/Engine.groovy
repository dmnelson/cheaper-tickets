package mn.david.cheapertickets.search.engine

import mn.david.cheapertickets.domain.Ticket
import mn.david.cheapertickets.search.SearchQuery

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 8:50 PM
 */
public interface Engine {

    Searcher searcherFor(SearchQuery searchData)

    Collection<Ticket> search(SearchQuery searchQuery)

}