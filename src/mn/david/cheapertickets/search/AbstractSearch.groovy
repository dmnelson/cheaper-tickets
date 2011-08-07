package mn.david.cheapertickets.search

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:04 PM
 */
abstract class AbstractSearch implements Search {

    private boolean isComplete = false;


     boolean isComplete(){
        return isComplete;
    }

    protected void completed(){
        this.isComplete = true;
    }
}
