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

    protected static Configuration configurationInstance;
    protected ConfigObject config;

    protected URL loadCustomConfigPlainFile() {
        ClassLoader.getSystemClassLoader().getResource("cheapertickets_config.groovy");
    }

    protected Script loadCustomConfigScript() {
        try {
            Class scriptOnDefaultPackage = Class.forName('cheapertickets_config');
            if (scriptOnDefaultPackage instanceof Script)
                return (Script) scriptOnDefaultPackage.newInstance();
            else {
                log.warn('"cheapertickets_config" not a Script class')
                throw new IllegalStateException('Invalid cheapertickets_config file/script.')
            }
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    protected Script loadDefaultConfigScript() {
        new cheapertickets_config();
    }

    public void build() {
        URL customConfigFile = loadCustomConfigPlainFile();
        if (customConfigFile) {
            log.debug('Found config file: "/cheapertickets_config.groovy".')
            build(customConfigFile);
        } else {
            def configScript = loadCustomConfigScript();
            if (configScript) {
                log.debug('Found config script on default package')
            } else {
                log.debug('Not found custom configuration script.')
                configScript = loadDefaultConfigScript();
                log.debug('Returning default configuration script.')
            }
            build(configScript);
        }
    }

    public void build(URL file) {
        def configSlurper = new ConfigSlurper();
        this.config = configSlurper.parse(file);
    }

    public void build(Script script) {
        def configSlurper = new ConfigSlurper();
        this.config = configSlurper.parse(script);
    }

    def getConfig() {
        config;
    }

    protected void setConfig(ConfigObject config) {
        this.config = config;
    }

    synchronized static get(Closure c) {
        if (!configurationInstance) {
            log.info("Creating new Configuration instance")
            configurationInstance = new Configuration();
            configurationInstance.build();
        }
        c.delegate = configurationInstance.config;
        c.setResolveStrategy(Closure.DELEGATE_FIRST);
        return c.call(configurationInstance.config);
    }
}
