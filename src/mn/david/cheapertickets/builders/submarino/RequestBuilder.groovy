package mn.david.cheapertickets.builders.submarino

import net.sf.json.JSONObject
import net.sf.json.groovy.JsonGroovyBuilder
import mn.david.cheapertickets.request.Request

/**
 * User: dnelson
 * Date: 7/28/11
 * Time: 12:36 PM
 */
abstract class RequestBuilder<T extends Request> {

    protected T request;

    protected  JSONObject jsonBuilder(def args){
        new JsonGroovyBuilder().json args;
    }

    public JSONObject toJSON(){
        jsonBuilder(){
            req = [
                PointOfSale : request.pointOfSale,
                UserBrowser : request.userBrowser
            ]
        };
    }

    public String toJSONString(){
        return toJSON().toString();
    }
}
