package mn.david.cheapertickets

/**
 * User: David
 * Date: 16/07/11
 * Time: 20:22
 */

def tickets = new Finder().airfares {
    from BHZ to POA at '22/09/2011'
}


println "-- Tickets"
tickets?.each {
    println it;
}