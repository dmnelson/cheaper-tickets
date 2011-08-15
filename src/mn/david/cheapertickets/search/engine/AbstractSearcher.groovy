package mn.david.cheapertickets.search.engine

import mn.david.cheapertickets.search.engine.Searcher
import mn.david.cheapertickets.search.SearchQuery

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:04 PM
 */
abstract class AbstractSearcher implements Searcher {

    protected boolean isComplete = false;
    protected SearchQuery searchQuery;
    List results;

    public AbstractSearcher(SearchQuery searchQuery) {
        this.searchQuery = searchQuery;
    }

    boolean isComplete() {
        return isComplete;
    }

    protected void completed() {
        this.isComplete = true;
    }

    protected void setResults(List results) {
        this.results = results;
    }

    List search() {
        if (requestSearch()) {
            while (!isComplete()) {
                Thread.sleep(1000);
                updateResults();
            }
        }
        return getResults();
    }

    void reset() {
        isComplete = false;
        results = null;
    }
}
