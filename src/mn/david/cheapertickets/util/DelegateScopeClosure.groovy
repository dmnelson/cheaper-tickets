package mn.david.cheapertickets.util

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 10/08/11
 * Time: 23:27
 */
class DelegateScopeClosure {

    Closure closure;

    public DelegateScopeClosure(Closure closure) {
        this.closure = closure
    }

    def call(theDelegate) {
        closure.delegate = theDelegate;
        closure.resolveStrategy = Closure.DELEGATE_FIRST;
        closure.call(theDelegate);
    }
}
