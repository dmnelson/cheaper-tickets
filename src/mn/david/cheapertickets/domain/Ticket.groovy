package mn.david.cheapertickets.domain

import java.text.DateFormat

/**
 * User: David
 * Date: 07/08/11
 * Time: 15:48
 */
class Ticket implements Comparable<Ticket> {

    City origin;
    City destination;
    BigDecimal cost;
    Date departure;

    int compareTo(Ticket o) {
        cost?.compareTo(o?.cost);
    }

    @Override
    String toString() {
        DateFormat format = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"))
        "${origin?.name} -> ${destination?.name} | ${format.format(departure)} - R\$ ${cost}"
    }


}