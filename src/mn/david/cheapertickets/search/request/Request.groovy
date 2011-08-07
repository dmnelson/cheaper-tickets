package mn.david.cheapertickets.search.request

import net.sf.json.JSONObject
import net.sf.json.groovy.JsonGroovyBuilder

/**
 * User: David
 * Date: 16/07/11
 * Time: 23:00
 */
abstract class Request {
    String pointOfSale = 'SUBMARINO';
    String userBrowser = 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30';

    public JSONObject toJSON(){
        jsonBuilder(){
            req = [
                PointOfSale : this.pointOfSale,
                UserBrowser : this.userBrowser
            ]
        };
    }

    protected static JSONObject jsonBuilder(def args){
        new JsonGroovyBuilder().json args;
    }

    public String toString(){
        return toJSON().toString();
    }
}
