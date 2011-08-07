package mn.david.cheapertickets.search

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:35 PM
 */
class SearchException extends RuntimeException {

    public SearchException(Throwable throwable) {
        super(throwable);
    }

    public SearchException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SearchException(String s) {
        super(s);
    }

    public SearchException() {}
}
