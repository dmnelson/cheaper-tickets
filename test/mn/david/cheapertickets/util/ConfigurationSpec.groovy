package mn.david.cheapertickets.util

import spock.lang.Specification
import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.StubFor

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/6/11
 * Time: 10:45 PM
 */
class ConfigurationSpec extends Specification {

    def "retrieving a configuration"() {

        given:  "A Configuration class that returns an ordinary mock"
            def mock = mockForConfigObject();
            Configuration.metaClass.'static'.get = {
                return mock.proxyInstance();
            }

        when: "A configuration entry is acessed"
            def configuration = Configuration.get();
            def aConfig = configuration.cheaperTickets.a.config;

        then: "The value should be a ConfigObject and not null"
            aConfig != null;
            aConfig instanceof ConfigObject;
            mock.verify(configuration);

        cleanup: "Undoing metaclass changes"
            Configuration.metaClass = null;

    }


    def "loading all configuration from a file"(){
        given: "A temp file"
            File configTempFile = createTempConfigFile("""
                                        my {
                                            test {
                                                config = 'WooHoo'
                                            }
                                        }
                                    """);

        and: "A mocked 'getConfigFile' method that returns that file"
            Configuration.metaClass.'static'.getConfigFile = {
                configTempFile.toURL();
            }

        when: "The deepest configuration is acessed"
            def deepestConfig = Configuration.get().my.test.config;

        then: "The value of the configuration should be the same as defined on the file"
            deepestConfig == 'WooHoo';

        when: "One of the intermediate level configuration is acessed"
            def anotherConfig = Configuration.get().my.test

        then: "A wrapper object should be returned"
            anotherConfig instanceof ConfigObject

        cleanup: "Deleting the file and reseting the Configuration metaclass"
            configTempFile.delete();
            Configuration.metaClass = null;
    }

    private static mockForConfigObject() {
        def mock = new MockFor(ConfigObject);
        mock.demand.containsKey(0..1) { key -> true }
        mock.demand.get(0..1) { name ->
            mock.proxyInstance();
        }
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