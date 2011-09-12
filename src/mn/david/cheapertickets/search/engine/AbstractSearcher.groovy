package mn.david.cheapertickets.search.engine

import mn.david.cheapertickets.search.InvalidQueryException
import mn.david.cheapertickets.search.SearchQuery

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:04 PM
 */
abstract class AbstractSearcher implements Searcher {

    protected boolean isComplete = false;
    protected boolean hasErrors = false;
    protected SearchQuery searchQuery;
    Collection results;

    public AbstractSearcher(SearchQuery searchQuery) {
        if (!searchQuery)
            throw new IllegalArgumentException("Search query must not be null");

        this.searchQuery = searchQuery;
    }

    boolean isComplete() {
        return isComplete;
    }

    boolean hasErrors() {
        return hasErrors;
    }

    protected void completed() {
        this.isComplete = true;
    }

    protected void setResults(Collection results) {
        this.results = results;
    }

    protected boolean shouldContinueUpdating() {
        return !hasErrors && !isComplete;
    }

    Collection search() {
        if (!searchQuery.validate()) {
            throw new InvalidQueryException("Invalid SearchQuery state");
        }
        if (requestSearch()) {
            while (shouldContinueUpdating()) {
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
