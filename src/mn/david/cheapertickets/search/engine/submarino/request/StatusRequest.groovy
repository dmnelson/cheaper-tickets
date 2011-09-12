package mn.david.cheapertickets.search.engine.submarino.request

import net.sf.json.JSONObject

/**
 * User: David
 * Date: 16/07/11
 * Time: 23:15
 */
class StatusRequest extends Request {

    String searchId;
    URL pullStatusFrom;

    StatusRequest(String searchId, URL pullStatusFrom) {
        if (!searchId)
            throw new IllegalStateException("SearchId must be NOT null. Call 'requestSearch' before.")

        if (!pullStatusFrom)
            throw new IllegalStateException("PullStatusFrom must be NOT null.")

        this.searchId = searchId
        this.pullStatusFrom = pullStatusFrom
    }

    @Override
    JSONObject toJSON() {
        JSONObject root = super.toJSON()
        root.req << jsonBuilder() {
            SearchId = this.searchId;
        }
        root.pullStatusFrom = this.pullStatusFrom.toString();
        return root;
    }
}
