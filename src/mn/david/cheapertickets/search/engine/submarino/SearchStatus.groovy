package mn.david.cheapertickets.search.engine.submarino

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/17/11
 * Time: 4:10 PM
 */
enum SearchStatus {

    ON_PROGRESS, COMPLETE

    static SearchStatus getAt(int i) {
        values()[i]
    }
}
