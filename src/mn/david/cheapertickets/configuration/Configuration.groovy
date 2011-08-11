package mn.david.cheapertickets.configuration

import groovy.util.logging.Commons

import mn.david.cheapertickets.util.DelegateScopeClosure

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:57 PM
 */
@Commons
class Configuration {

    protected static Configuration configurationInstance;
    ConfigObject config;

    protected URL loadCustomConfigPlainFile() {
        URL customFile = ClassLoader.getSystemClassLoader().getResource("default_config.groovy");
        mn.david.cheapertickets.configuration.Configuration.log.debug(customFile ? 'Found config file: "/default_config.groovy".' : 'Not found custom configuration file.');
        return customFile;
    }

    protected Script loadCustomConfigScript() {
        try {
            Class scriptOnDefaultPackage = Class.forName('cheapertickets_config');
            mn.david.cheapertickets.configuration.Configuration.log.debug('Found config script on default package');
            if (scriptOnDefaultPackage instanceof Script)
                return (Script) scriptOnDefaultPackage.newInstance();
            else {
                mn.david.cheapertickets.configuration.Configuration.log.warn('"cheapertickets_config" not a Script class')
                throw new IllegalStateException('Invalid cheapertickets_config file/script.')
            }
        } catch (ClassNotFoundException e) {
            mn.david.cheapertickets.configuration.Configuration.log.debug('Not found custom configuration script.')
            return null;
        }
    }

    protected Script loadDefaultConfigScript() {
        mn.david.cheapertickets.configuration.Configuration.log.debug('Returning default configuration script.')
        new default_config();
    }

    def build() {
        URL customConfigFile = loadCustomConfigPlainFile();
        if (customConfigFile) {
            return build(customConfigFile);
        } else {
            def configScript = loadCustomConfigScript() ?: loadDefaultConfigScript();
            return build(configScript);
        }
    }

    def build(URL file) {
        this.config = new ConfigSlurper().parse(file);
        return this;
    }

    def build(Script script) {
        this.config = new ConfigSlurper().parse(script);
        return this;
    }

    protected void setConfig(ConfigObject config) {
        this.config = config;
    }

    static get(Closure c) {
        synchronized (Configuration) {
            if (!configurationInstance) {
                mn.david.cheapertickets.configuration.Configuration.log.info("Creating new Configuration instance")
                configurationInstance = new Configuration().build();
            }
        }
        def closureDelegate = new DelegateScopeClosure(c);
        return closureDelegate.call(configurationInstance.config);
    }
}
