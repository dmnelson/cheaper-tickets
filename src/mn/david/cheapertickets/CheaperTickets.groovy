package mn.david.cheapertickets

/**
 * User: David
 * Date: 16/07/11
 * Time: 20:22
 */

def allTickets = new TreeSet();

['16/09/2011'].each { date ->
    tickets = new Finder().airfares {
        from POA to BHZ at date
    }
    allTickets.addAll(tickets)
}

println "-- Tickets"
allTickets?.findAll{ it.departure.hours > 12 }.each {
    println it;
}