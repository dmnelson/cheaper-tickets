package mn.david.cheapertickets.util

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:57 PM
 */
class Configuration {

    private static ConfigObject config;

    protected static getConfigFile(){
        def url = Configuration.getResource("/cheaper-tickets.gcfg");
        println Configuration.getResource("/")
        println url;
        return url;
    }

    synchronized static ConfigObject get() {
        if (!config)
            config = new ConfigSlurper().parse(configFile)

        return config;
    }
}
