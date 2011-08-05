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

    static Destination getDestination(String value) {
        Destination valueOfEnum = Enum.valueOf(Destination, value);
        if(!valueOfEnum){
            for (it in Destination.values()) {
                if (it.name.equalsIgnoreCase(value)) {
                    return it;
                }
            }
            return null;
        }
        return valueOfEnum;
    }
}
