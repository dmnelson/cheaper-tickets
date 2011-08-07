package mn.david.cheapertickets.search.engine

import mn.david.cheapertickets.search.Search

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 8:50 PM
 */
public interface Engine {

    Search searchFor(searchData);

}