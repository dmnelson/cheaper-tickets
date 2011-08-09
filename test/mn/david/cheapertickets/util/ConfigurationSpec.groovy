package mn.david.cheapertickets.util

import spock.lang.Specification
import groovy.mock.interceptor.MockFor
import groovy.util.logging.Commons

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/6/11
 * Time: 10:45 PM
 */
@Commons
class ConfigurationSpec extends Specification {

    def "Retrieving a configuration"() {

        given:  "A Configuration class that returns an ordinary mock"
            log.info 'Test: Retrieving a configuration'
            def mock = mockForConfigObject();
            Configuration.metaClass.'static'.getConfig = {
                return mock.proxyInstance();
            }

        when: "A configuration entry is acessed"
            def configuration = Configuration.getConfig();
            def aConfig = configuration.cheaperTickets.a.config;

        then: "The value should be a ConfigObject and not null"
            aConfig != null;
            aConfig instanceof ConfigObject;
            mock.verify(configuration);

        cleanup: "Undoing metaclass changes"
            Configuration.metaClass = null;
            Configuration.config = null;

    }


    def "Loading all configuration from a file"(){

        given: "A temp file"
            log.info 'Test: Loading all configuration from a file'
            File configTempFile = createTempConfigFile("""
                                        my {
                                            test {
                                                config = 'WooHoo'
                                            }
                                        }
                                    """);
            Configuration.metaClass.'static'.getConfigScript = {
                configTempFile.toURL();
            }

        and: "A mocked 'getConfigScript' method that returns that file"


        when: "The deepest configuration is acessed"
            def deepestConfig = Configuration.config.my.test.config;
            log.info "Deepest config: ${deepestConfig}"

        then: "The value of the configuration should be the same as defined on the file"
            deepestConfig == 'WooHoo';

        when: "One of the intermediate level configuration is acessed"
            def anotherConfig = Configuration.config.my.test
            log.info "Intermediate config: ${anotherConfig}"

        then: "A wrapper object should be returned"
            anotherConfig instanceof ConfigObject

        cleanup: "Deleting the file and reseting the Configuration metaclass"
            configTempFile.delete();
            Configuration.metaClass = null;
            Configuration.config = null;
    }

    def "Loading from actual config file" (){

        given:
            log.info 'Test: Loading from actual config file'
            def configFile = Configuration.getConfigScript();
            def config = Configuration.config;
            def configMap = config?.flatten();

        expect:
            configFile != null;
            config != null;
            config instanceof ConfigObject;
            !configMap.isEmpty()

        cleanup:
            Configuration.config = null;
    }



    private static mockForConfigObject() {
        def mock = new MockFor(ConfigObject);
        mock.demand.containsKey(0..1) { key -> true }
        mock.demand.get(0..1) { name -> mock.proxyInstance(); }
        return mock;
    }

    private static createTempConfigFile(def contents) {
        File configTempFile = new File(System.getProperty('java.io.tmpdir'), 'cheaper-tickets-configfile.groovy');
        configTempFile.withWriter { w ->
            w << contents
        }
        return configTempFile;
    }
}