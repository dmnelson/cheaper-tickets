package mn.david.cheapertickets.search

import mn.david.cheapertickets.domain.City
import mn.david.cheapertickets.configuration.Configuration

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/11/11
 * Time: 5:44 PM
 */
class SearchQuery {
    City origin;
    City destination;
    Date departureDate;
    String dateFormat = Configuration.get { cheaperTickets.dateFormat }

    static {
        SearchQuery.metaClass.getProperty = { name ->
            def metaProperty = SearchQuery.metaClass.getMetaProperty(name)
            metaProperty ? metaProperty.getProperty(delegate) : City.getCity(name);
        }
    }

    def from(City city) {
        origin = city;
        return this;
    }

    def to(City city) {
        destination = city;
        return this;
    }

    def from(String city) {
        from getCity(city);
    }

    def to(String city) {
        to getCity(city);
    }

    def at(String date) {
        departureDate = Date.parse(dateFormat, date);
        return this
    }

    private static City getCity(String nameOrCode) {
        def aCity = City.getCity(nameOrCode);
        if (!aCity) {
            throw new IllegalArgumentException("Invalid city, must be existent. Check 'City.values()'")
        }
        return aCity;
    }

}
