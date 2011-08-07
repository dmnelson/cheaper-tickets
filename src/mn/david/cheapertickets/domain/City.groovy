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

<<<<<<< HEAD
    static Destination get(String value) {
        def valueByCode = Destination.valueOf value;
        return valueByCode ?: Destination.values().find { it.name.equalsIgnoreCase(value) }
=======
    static City getDestination(String value) {
        City valueOfEnum = Enum.valueOf(City, value);
        if(!valueOfEnum){
            for (it in City.values()) {
                if (it.name.equalsIgnoreCase(value)) {
                    return it;
                }
            }
            return null;
        }
        return valueOfEnum;
>>>>>>> 25d41cb38531f7e1a4c26d2cb7c2f8a0b5d9e85e
    }

}
