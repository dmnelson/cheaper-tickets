package mn.david.cheapertickets.util

import spock.lang.Specification
import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.StubFor

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 18/08/11
 * Time: 22:57
 */
class DelegateScopeClosureSpec extends Specification {

    static class Delegate {

        public void test(arg) {
            throw new UnsupportedOperationException();
        }

        public void two(args) {
            throw new UnsupportedOperationException();
        }
    }

    def "Calling closure"() {

        setup:
            def mock = new StubFor(Delegate, false)
            mock.demand.test { assert it == "it" };
            mock.demand.two { assert it == "times" };
            def closure = {
                test "it"
                two "times"
            }
            def closureDelegate = new DelegateScopeClosure(closure);

        when:
            mock.use {
                def delegate = new Delegate();
                closureDelegate.call(delegate);
            }

        then:
            mock.expect.verify();

    }

}
