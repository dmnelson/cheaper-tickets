package mn.david.cheapertickets.domain

import java.text.DateFormat
import groovy.transform.EqualsAndHashCode

/**
 * User: David
 * Date: 07/08/11
 * Time: 15:48
 */

@EqualsAndHashCode
class Ticket implements Comparable<Ticket> {

    City origin;
    City destination;
    BigDecimal cost;
    Date departure;
    Date arrival;
    String company;

    int compareTo(Ticket o) {
        origin <=> o.origin ?:
            destination <=> o.destination ?:
                cost <=> o.cost ?:
                    departure <=> o.departure ?:
                        arrival <=> o.arrival ?:
                            company <=> o.company;
    }


    @Override
    String toString() {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale("pt", "BR"))
        "${origin?.name} -> ${destination?.name} | ${departure ? format.format(departure) : '--'} - ${arrival ? format.format(arrival) : '--'} | R\$ ${cost} - ($company)"
    }


}