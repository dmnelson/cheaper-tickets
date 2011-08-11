package mn.david.cheapertickets.search.engine

import mn.david.cheapertickets.search.engine.Searcher
import mn.david.cheapertickets.search.SearchQuery

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:04 PM
 */
abstract class AbstractSearcher implements Searcher {

    private boolean isComplete = false;
    protected SearchQuery searchQuery;

    public AbstractSearcher(SearchQuery searchQuery){
         this.searchQuery = searchQuery;
    }

    boolean isComplete() {
        return isComplete;
    }

    protected void completed() {
        this.isComplete = true;
    }
}
