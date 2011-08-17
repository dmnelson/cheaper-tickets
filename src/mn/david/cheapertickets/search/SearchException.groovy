package mn.david.cheapertickets.search

import groovy.transform.InheritConstructors

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/5/11
 * Time: 9:35 PM
 */
@InheritConstructors
class SearchException extends RuntimeException {

    List errors;

    String toString(){
        "${super.toString()}; Errors: ${errors}"
    }

}
