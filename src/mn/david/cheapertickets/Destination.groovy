package mn.david.cheapertickets

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 7/31/11
 * Time: 10:48 PM
 */
enum Destination {

    POA('Porto Alegre'),
    BHZ('Belo Horizonte')

    final String name;

    private Destination(String name) {
        this.name = name;
    }

    static Destination get(String value) {
        def valueByCode = Destination.valueOf value;
        return valueByCode ?: Destination.values().find { it.name.equalsIgnoreCase(value) }
    }

}
