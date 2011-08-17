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
    Date arrival;
    String company;

    int compareTo(Ticket o) {
        cost?.compareTo(o?.cost);
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o instanceof Ticket)) return false;

        Ticket ticket = (Ticket) o;

        if (arrival != ticket.arrival) return false;
        if (company != ticket.company) return false;
        if (cost != ticket.cost) return false;
        if (departure != ticket.departure) return false;
        if (destination != ticket.destination) return false;
        if (origin != ticket.origin) return false;

        return true;
    }

    int hashCode() {
        int result;
        result = (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (arrival != null ? arrival.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        return result;
    }

    @Override
    String toString() {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale("pt", "BR"))
        "${origin?.name} -> ${destination?.name} | ${departure ? format.format(departure) : '--'} - R\$ ${cost}"
    }


}