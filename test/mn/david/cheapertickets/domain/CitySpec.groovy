package mn.david.cheapertickets.domain

import spock.lang.Specification

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 18/08/11
 * Time: 22:40
 */
class CitySpec extends Specification {

    def "Static finder"(){

        expect:
            City.getCity('BHZ') == City.BHZ
            City.getCity('Belo Horizonte') == City.BHZ
            City.forName('Belo Horizonte') == City.BHZ
            City.forName('BHZ') == null
            City.getCity("XXX") == null
            City.forName("XYZ") == null
    }

}
