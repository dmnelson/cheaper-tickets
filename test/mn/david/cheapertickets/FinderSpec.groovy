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

        def allTickets = new TreeSet();
        ['02/09/2011', '09/09/2011', '16/09/2011', '23/09/2011', '30/09/2011'].each { date ->
            def tickets = new Finder().airfares {
                from BHZ to POA at date
            }
            allTickets.addAll(tickets)
            tickets = new Finder().airfares {
                from POA to BHZ at date
            }
            allTickets.addAll(tickets)
        }

         ['04/09/2011', '11/09/2011', '18/09/2011', '25/09/2011', '02/10/2011'].each { date ->
           def tickets = new Finder().airfares {
                from BHZ to POA at date
            }
            allTickets.addAll(tickets)
            tickets = new Finder().airfares {
                from POA to BHZ at date
            }
            allTickets.addAll(tickets)
        }

        println "-- Tickets"
        allTickets?.each {
            println it;
        }



        expect:
        1 == 1
    }
}
