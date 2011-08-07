package mn.david.cheapertickets.search.request

import net.sf.json.JSONObject

/**
 * User: David
 * Date: 16/07/11
 * Time: 23:15
 */
class StatusRequest extends Request {
    String searchId;
    URL pullStatusFrom;

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
