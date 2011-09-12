package mn.david.cheapertickets.search.engine

import mn.david.cheapertickets.domain.Ticket
import mn.david.cheapertickets.search.SearchQuery
import spock.lang.Specification

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 07/09/11
 * Time: 23:20
 */
class EngineFactorySpec extends Specification {

    def config;
    EngineFactory engineFactory;

    def setup() {
        config = new ConfigSlurper().parse(Config);
        engineFactory = new EngineFactory(config);
    }

    def "default engine"() {
        expect:
            engineFactory.defaultEngineName == 'someEngine'
            engineFactory.defaultEngine.class == MockEngine;
    }

    def "engine list"(){
        expect:
            engineFactory.availableEngineNames == ['someEngine', 'anotherEngine', 'incompleteEngine',
                    'invalidEngine', 'invalidEngine2', 'invalidEngine3']
    }

    def "get a engine"(){

        when:
            def engine = engineFactory.getEngine('anotherEngine');

        then:
            engine.class == MockEngine;

        when: "inexistent engine"
            engineFactory.getEngine('inexistent');

        then:
            thrown(IllegalArgumentException);

        when: "a engine that has no clas configured"
            engineFactory.getEngine('incompleteEngine');

        then:
            thrown(IllegalArgumentException);

        when: "a engine with a invalid Class configured"
            engineFactory.getEngine('invalidEngine');

        then:
            thrown(ClassCastException);

        when: "a engine with a inexistent class configured"
           engineFactory.getEngine('invalidEngine2');

        then:
            thrown(ClassNotFoundException);

         when: "a engine with a name of a invalid class"
           engineFactory.getEngine('invalidEngine3');

        then:
            thrown(ClassCastException);
    }


    class Config extends Script {

        @Override
        Object run() {
            defaultEngine = 'someEngine'
            engine {
                someEngine {
                    engineClass = MockEngine
                }
                anotherEngine {
                    engineClass = MockEngine
                }
                incompleteEngine{

                }
                invalidEngine{
                    engineClass = Object
                }
                invalidEngine2{
                    engineClass = 'Object'
                }
                invalidEngine3{
                    engineClass = 'java.lang.Object'
                }
            }
        }
    }

    class InvalidConfig extends Script{
        @Override
        Object run() {
            defaultEngine = 'test'
        }

    }
    class MockEngine implements Engine {
        Searcher searcherFor(SearchQuery searchData) { null}
        Collection<Ticket> search(SearchQuery searchQuery) { null }
    }
}

