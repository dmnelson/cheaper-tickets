package mn.david.cheapertickets.domain

import spock.lang.Specification

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 18/08/11
 * Time: 22:40
 */
class CitySpec extends Specification {

    def "Static finder by code or name"(){

        expect:
            City.getCity(nameOrCode) == city

        where:
            nameOrCode          |   city
            'Belo Horizonte'    |   City.BHZ
            'BHZ'               |   City.BHZ
            'Porto Alegre'      |   City.POA
            'XYZ'               |   null
            'PORTO alegre'      |   City.POA
    }

    def "Static finder only by name"(){

        expect:
            City.forName(name) == city

        where:
            name                |   city
            'BHZ'               |   null
            'Belo Horizonte'    |   City.BHZ
            'Porto Alegre'      |   City.POA
            'XYZ'               |   null
            'PORTO alegre'      |   City.POA
    }

}
