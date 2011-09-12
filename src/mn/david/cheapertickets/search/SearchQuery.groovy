package mn.david.cheapertickets.search

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import mn.david.cheapertickets.configuration.Configuration
import mn.david.cheapertickets.domain.City

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 8/11/11
 * Time: 5:44 PM
 */
@EqualsAndHashCode(excludes = 'dateFormat')
@ToString(excludes = 'dateFormat')
class SearchQuery {

    City origin;
    City destination;
    Date departureDate;
    private String dateFormat;

    static {
        SearchQuery.metaClass.getProperty = { name ->
            def metaProperty = SearchQuery.metaClass.getMetaProperty(name)
            metaProperty ? metaProperty.getProperty(delegate) : City.getCity(name);
        }
    }

    def from(City city) {
        if (!city) {
            throw new IllegalArgumentException("Invalid city, must be existent. Check 'City.values()'")
        }
        origin = city;
        return this;
    }

    def to(City city) {
        if (!city) {
            throw new IllegalArgumentException("Invalid city, must be existent. Check 'City.values()'")
        }
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
        departureDate = Date.parse(getDateFormat(), date);
        return this
    }

    def at(args) {
        if (args.any) {

        }
        return this;
    }

    void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    String getDateFormat() {
        if (!dateFormat) {
            dateFormat = Configuration.get { cheaperTickets.dateFormat }
        }
        return dateFormat;
    }

    private static City getCity(String nameOrCode) {
        City.getCity(nameOrCode);
    }

    boolean validate() {
        if (departureDate && departureDate <= (new Date() - 1)) {
            return false;
        }
        if (!origin || !destination) {
            return false;
        }
        return true;
    }
}
