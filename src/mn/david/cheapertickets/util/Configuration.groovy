package mn.david.cheapertickets.util

import groovy.util.logging.Commons
import mn.david.cheapertickets.cheapertickets_config

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:57 PM
 */
@Commons
class Configuration {

    protected static ConfigObject config;

    protected static getConfigScript() {
        URL configFile = ClassLoader.getSystemClassLoader().getResource("/cheapertickets_config.groovy");
        if (!configFile) {
            log.debug('"cheapertickets_config.groovy" text file NOT found on classpath.')
            try {
                Class scriptOnDefaultPackage = Class.forName('cheapertickets_config');
                log.debug('Found config script on default package')
                if(scriptOnDefaultPackage instanceof Script)
                    return scriptOnDefaultPackage.newInstance();
                else
                    log.warn('"cheapertickets_config" not a Script class')
            } catch (e) {
                log.debug('Not found custom configuration script.')
            }
            log.debug('Returning default configuration script.')
            return new cheapertickets_config();
        } else {
            log.debug('Found config file: "/cheapertickets_config.groovy".')
            return configFile;
        }
    }

    synchronized static ConfigObject getConfig() {
        if (!config) {
            def fileURL = configScript;
            if (fileURL)
                config = new ConfigSlurper().parse(fileURL)
        }
        return config ?: new ConfigObject();
    }
}
