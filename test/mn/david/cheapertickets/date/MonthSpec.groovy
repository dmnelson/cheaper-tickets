package mn.david.cheapertickets.date

import spock.lang.Specification
import static mn.david.cheapertickets.date.Month.*

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 10/09/11
 * Time: 01:18
 */
class MonthSpec extends Specification {

    def "retrieving current month"(){
        expect:
            currentMonth.ordinal() == Calendar.getInstance().get(Calendar.MONTH)
    }

    def "sum of months"(){

        setup:
            def september = SEPTEMBER;

        expect:
            september + i == month;

        where:
            i   | month
            1   | OCTOBER
            2   | NOVEMBER
            3   | DECEMBER
            4   | JANUARY
            12  | SEPTEMBER
            15  | DECEMBER
            20  | MAY
            30  | MARCH
            36  | SEPTEMBER
    }

    def "subtraction of months"(){

        setup:
            def march = MARCH;

        expect:
            march - i == month;

        where:
            i   | month
            1   | FEBRUARY
            2   | JANUARY
            3   | DECEMBER
            4   | NOVEMBER
            12  | MARCH
            15  | DECEMBER
            20  | JULY
            30  | SEPTEMBER
            36  | MARCH
    }

    def "next month"(){

        given:
            def currentMonth = Month.currentMonth

        when:
            def nextMonth = currentMonth.next();

        then:
            nextMonth == currentMonth + 1;
    }
}
