package mn.david.cheapertickets

import mn.david.cheapertickets.domain.City
import mn.david.cheapertickets.search.SearchQuery
import spock.lang.Specification

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 07/08/11
 * Time: 20:10
 */
class FinderSpec extends Specification {

    def "Dummy"() {
        setup:

        def tickets = new Finder().airfares {
            from POA to BHZ at '23/09/2011'
        }

        println "-- Tickets"
        tickets?.each{
            println it;
        }


        expect:
            1==1
    }
}
