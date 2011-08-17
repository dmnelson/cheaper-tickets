package mn.david.cheapertickets.util

import mn.david.cheapertickets.search.engine.Engine
import mn.david.cheapertickets.configuration.Configuration

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/17/11
 * Time: 10:49 AM
 */
class EngineFactory {

    public static String getDefaultEngineName() {
        Configuration.get { cheaperTickets.defaultEngine }
    }

    public static Engine getEngine(String engineName) {
        def engineClassConfig = Configuration.get { cheaperTickets.engine[engineName].engineClass };

        Class engineClass;
        if (engineClassConfig instanceof String) {
            engineClass = Class.forName(engineClassConfig);
        } else if (engineClassConfig instanceof Class) {
            engineClass = engineClassConfig;
        } else {
            throw new IllegalArgumentException("Invalid engine configuration type: ${engineClassConfig}")
        }
        if (Engine.isAssignableFrom(engineClass)) {
            return (Engine) engineClass.newInstance();
        } else {
            throw new IllegalArgumentException("Invalid engine class: ${engineClass}")
        }
    }

    public static Engine getDefaultEngine() {
        getEngine(getDefaultEngineName())
    }
}
