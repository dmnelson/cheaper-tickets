package mn.david.cheapertickets.domain

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 7/31/11
 * Time: 10:48 PM
 */
enum City {

    POA('Porto Alegre'),
    BHZ('Belo Horizonte')

    final String name;

    private City(String name) {
        this.name = name;
    }

    static City get(String value) {
        def valueByCode = valueOf value;
        return valueByCode ?: values().find { it.name.equalsIgnoreCase(value) }
    }

}
