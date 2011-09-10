package mn.david.cheapertickets.date

/**
 * User: David Nelson <http://github.com/dmnelson>
 * Date: 20/08/11
 * Time: 19:22
 */
enum Month {

    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
    JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER


    Month plus(int i) {
        int sum = ordinal() + i;
        int numOfMonths = values().size();
        return Month[sum % numOfMonths];
    }

    Month minus(int i) {
        plus(i * -1);
    }

    Month next() {
        return this + 1;
    }

    static Month getAt(int i) {
        return values()[i];
    }

    static Month getCurrentMonth() {
        return Month[Calendar.getInstance().get(Calendar.MONTH)]
    }



}
