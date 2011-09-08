package mn.david.cheapertickets.domain

import groovy.transform.ToString

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 7/31/11
 * Time: 10:48 PM
 */

@ToString
enum City  {

    POA('Porto Alegre'),
    BHZ('Belo Horizonte')

    final String name;

    private City(String name) {
        this.name = name;
    }

    static City getCity(String value) {
        def valueByCode = values().find{ it.name() == value};
        valueByCode ?: forName(value)
    }

    static City forName(String name) {
        City.values().find { City city -> city.name.equalsIgnoreCase(name) };
    }


}
