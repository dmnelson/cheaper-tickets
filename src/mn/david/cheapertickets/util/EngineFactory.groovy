package mn.david.cheapertickets.util

import mn.david.cheapertickets.configuration.Configuration
import mn.david.cheapertickets.search.engine.Engine

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/17/11
 * Time: 10:49 AM
 */
class EngineFactory {

    public static String[] getAvailableEngineNames() {
        Configuration.get { cheaperTickets.engine }*.key as String[]
    }

    public static String getDefaultEngineName() {
        Configuration.get { cheaperTickets.defaultEngine }
    }

    public static Engine getEngine(String engineName) {
        def engineClassConfig = Configuration.get { cheaperTickets.engine[engineName].engineClass };
        def engineClass = getEngineClass(engineClassConfig);
        return engineClass.newInstance();
    }

    private static Class<? extends Engine> getEngineClass(String className) {
        Class.forName className asSubclass Engine;
    }

    private static Class<? extends Engine> getEngineClass(Class<? extends Engine> clazz) {
        clazz;
    }

    private static Class<? extends Engine> getEngineClass(def whatever) {
        throw new IllegalArgumentException("Invalid engine configuration type: ${whatever} - ${whatever?.class}")
    }

    public static Engine getDefaultEngine() {
        getEngine defaultEngineName;
    }
}
