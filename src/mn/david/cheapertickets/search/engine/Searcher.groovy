package mn.david.cheapertickets.search.engine

import mn.david.cheapertickets.search.engine.submarino.request.SearchRequest

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 8:53 PM
 */
public interface Searcher {

    boolean requestSearch()

    void updateResults()

    boolean isComplete();

    boolean hasErrors();

    Collection getResults();

    Collection search();

    void reset();
}
