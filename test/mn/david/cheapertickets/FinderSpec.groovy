package mn.david.cheapertickets

import spock.lang.Specification
import mn.david.cheapertickets.Finder.SearchQuery
import mn.david.cheapertickets.domain.City

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 07/08/11
 * Time: 20:10
 */
class FinderSpec extends Specification {

    def "Building search queries using DSL style"() {

        given: "A search query"
            def searchQuery = new SearchQuery();

        and: "with a brazilian date format"
            searchQuery.dateFormat = 'dd/MM/yyyy'

        when: "Origin and destination are set using the inject properties"
            searchQuery.with {
                from POA to BHZ
            }

        then: "They should be City enum instances"
            searchQuery.getOrigin() == City.POA
            searchQuery.getDestination() == City.BHZ

        when: "Origin and destination are set by their names"
            searchQuery.with {
                from 'Belo Horizonte' to 'Porto Alegre'
            }

        then: "They should keep being City instances"
            searchQuery.origin == City.BHZ
            searchQuery.destination == City.POA

        when: "Inexistent origin city is set"
             searchQuery.with {
                from 'Tagamandapio'
            }

        then: "A exception should be thown"
            thrown(IllegalArgumentException)

        when: "Inexistent destination city is set"
             searchQuery.with {
                to 'Tagamandapio'
            }

        then: "A exception should be thown again"
            thrown(IllegalArgumentException)

        when: "A departure date is set according to the specified date format"
            searchQuery.with {
                at '12/06/2011'
            }

        then: "A Date instance should be created properly"
            searchQuery.departureDate == Date.parse("yyyy-MM-dd", "2011-06-12")



    }
}
