package mn.david.cheapertickets.util

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:57 PM
 */
class Configuration {

    protected static ConfigObject config;

    protected static getConfigFile() {
        Configuration.getResource("/cheaper-tickets.gcfg");
    }

    synchronized static ConfigObject getConfig() {
        if (!config) {
            def fileURL = configFile;
            if (fileURL)
                config = new ConfigSlurper().parse(fileURL)
        }
        return config ?: new ConfigObject();
    }
}
