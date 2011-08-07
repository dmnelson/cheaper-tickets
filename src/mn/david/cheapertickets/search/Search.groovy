package mn.david.cheapertickets.search

import mn.david.cheapertickets.search.request.SearchRequest

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 8:53 PM
 */
public interface Search {

    void requestSearch(SearchRequest request)

    void updateResults()

    boolean isComplete()

    List getResults();
}
