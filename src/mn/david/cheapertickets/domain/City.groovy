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
    }
}
