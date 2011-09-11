package mn.david.cheapertickets.search.engine

import mn.david.cheapertickets.configuration.Configuration
import mn.david.cheapertickets.search.engine.Engine

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/17/11
 * Time: 10:49 AM
 */
class EngineFactory {

    private def config;

    public EngineFactory() {
        config = Configuration.get { cheaperTickets };
    }

    public EngineFactory(def config) {
        this.config = config;
    }

    public String[] getAvailableEngineNames() {
        config.engine*.key as String[]
    }

    public String getDefaultEngineName() {
        config.defaultEngine
    }

    public Engine getEngine(String engineName) {
        def engineClassConfig = config.engine[engineName].engineClass;
        def engineClass = getEngineClass(engineClassConfig);
        return engineClass.newInstance();
    }

    private Class<? extends Engine> getEngineClass(String className) {
        Class.forName className asSubclass Engine;
    }

    private Class<? extends Engine> getEngineClass(Class<? extends Engine> clazz) {
        clazz;
    }

    private Class<? extends Engine> getEngineClass(def whatever) {
        throw new IllegalArgumentException("Invalid engine configuration type: ${whatever} - ${whatever?.class}")
    }

    public Engine getDefaultEngine() {
        getEngine defaultEngineName;
    }
}
