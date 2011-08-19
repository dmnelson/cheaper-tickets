package mn.david.cheapertickets.domain

import spock.lang.Specification

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 18/08/11
 * Time: 22:20
 */
class TicketSpec extends Specification {

    def "Comparison between tickets"(){

        expect: "The first be 'lower' than the last"
            first < last

        where:
            first   |   last
            new Ticket(cost:10) | new Ticket(cost:20)
            new Ticket(cost:10, departure: new Date()) | new Ticket(cost:10, departure: new Date() + 1)
    }

    def "Ticket equality"(){

        setup: "Some tickets"
            def t1 = new Ticket(cost:10);
            def t2 = new Ticket(cost:10);
            def t3 = new Ticket(cost:10, departure: new Date());
            def t4 = new Ticket(cost:20);
            def t5 = new Ticket();
            def t6 = new Ticket();

        expect:
            t1 == t2
            t1 != t3
            t1 != t4
            t1 != t5
            t2 != t3
            t2 != t4
            t2 != t5
            t3 != t4
            t3 != t5
            t4 != t5
            t5 == t6

        and:
            t1.hashCode() == t2.hashCode()
            t1.hashCode() != t3.hashCode()
            t5.hashCode() == t5.hashCode()
            t1.hashCode() != t5.hashCode()
    }
}
