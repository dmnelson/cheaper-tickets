package mn.david.cheapertickets

/**
 * User: David
 * Date: 16/07/11
 * Time: 20:22
 */

def tickets = new Finder().airfares {
    from POA to BHZ at '06/01/2013'
}


println "-- Tickets"
tickets?.each {
    println it;
}