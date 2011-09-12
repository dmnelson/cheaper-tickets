package mn.david.cheapertickets

import mn.david.cheapertickets.domain.City
import mn.david.cheapertickets.search.SearchQuery
import spock.lang.Specification
import mn.david.cheapertickets.search.engine.Engine

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 07/08/11
 * Time: 20:10
 */
class FinderSpec extends Specification {

    def "Searching for tickets"() {

        given:
            Engine engine = Mock(Engine);
            Finder finder = new Finder(engine);

        when:
            finder.airfares {
                from 'Belo Horizonte' to 'Porto Alegre' at '12/06/2011'
            }

        then:
            1 * engine.search(new SearchQuery(origin: City.BHZ, destination: City.POA, departureDate: Date.parse('dd/MM/yyyy','12/06/2011')));
    }
}
