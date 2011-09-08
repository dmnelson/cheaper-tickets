package mn.david.cheapertickets

/**
 * User: David
 * Date: 16/07/11
 * Time: 20:22
 */

def allTickets = new TreeSet();

['02/09/2011', '09/09/2011', '16/09/2011', '23/09/2011', '30/09/2011'].each { date ->
    def tickets = new Finder().airfares {
        from BHZ to POA at date
    }
    allTickets.addAll(tickets)
    tickets = new Finder().airfares {
        from POA to BHZ at date
    }
    allTickets.addAll(tickets)
}

['04/09/2011', '11/09/2011', '18/09/2011', '25/09/2011', '02/10/2011'].each { date ->
    def tickets = new Finder().airfares {
        from BHZ to POA at date
    }
    allTickets.addAll(tickets)
    tickets = new Finder().airfares {
        from POA to BHZ at date
    }
    allTickets.addAll(tickets)
}

println "-- Tickets"
allTickets?.each {
    println it;
}