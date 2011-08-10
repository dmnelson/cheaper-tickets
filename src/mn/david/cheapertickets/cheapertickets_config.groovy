package mn.david.cheapertickets

cheaperTickets {

    dateFormat = 'dd/MM/yyyy'
    defaultEngine = 'submarino'

    engine {
        submarino {
            engineClass = mn.david.cheapertickets.search.engine.submarino.SubmarinoEngine
            webservice {
                baseURL = "http://www.submarinoviagens.com.br/Passagens/UIService/Service.svc/"
                search = "SearchGroupedFlights"
                status = "GetSearchStatus"
            }
        }
    }
}