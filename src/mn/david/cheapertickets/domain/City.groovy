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

    static City getCity(String value) {
        def valueByCode = values().find{ it.toString() == value};
        valueByCode ?: forName(value)
    }

    static City forName(String name) {
        City.values().find { City city -> city.name.equalsIgnoreCase(name) };
    }

    public static void main(String[] args) {
       println City.getCity('Teste')
    }
}
