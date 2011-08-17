package mn.david.cheapertickets.search.engine.submarino.response

import mn.david.cheapertickets.search.engine.submarino.SearchStatus

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 15/08/11
 * Time: 23:34
 */
abstract class Response {

    final String sessionId;
    final String searchId;
    final URL pullStatusFrom;
    final Collection errors;
    final SearchStatus status;

    Response(def response) {
        this.sessionId = response.SessionId;
        this.searchId = response.SearchId;
        this.pullStatusFrom = new URL(response.PullStatusFrom);
        this.status = SearchStatus[response.Status as int];
        this.errors = response.Errors;
    }

    boolean isCompleted() {
        status == SearchStatus.COMPLETE;
    }

}
