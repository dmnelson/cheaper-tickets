package mn.david.cheapertickets.builders.submarino

import mn.david.cheapertickets.request.SearchRequest
import net.sf.json.JSONObject
import mn.david.cheapertickets.Destination
import groovy.util.logging.Log
import java.util.logging.Logger

/**
 * User: dnelson
 * Date: 7/29/11
 * Time: 11:25 AM
 */
class SearchRequestBuilder extends RequestBuilder<SearchRequest> {

    private static Logger log = Logger.getLogger(SearchRequestBuilder.class.name);

    private String from;
    private String to;
    private Date at;

    SearchRequestBuilder(Closure c) {
        this.request = new SearchRequest();
        c.setDelegate(this);
        c.call();
    }

    static SearchRequestBuilder create(Closure c) {
        return new SearchRequestBuilder(c);
    }

    SearchRequestBuilder from(Destination from) {
        this.request.origin.code = from?.toString();
        return this;
    }



    SearchRequestBuilder to(Destination to) {
        this.request.destination.code = to?.toString();
        return this;
    }

    SearchRequestBuilder at(String at) {
        return at(Date.parse('dd/MM/yyyy', at));
    }

    SearchRequestBuilder at(Date at) {
        this.request.origin.departureDay = at;
        return this;
    }

    SearchRequestBuilder returnAt(String at){
        this.request.destination.departureDay = Date.parse('dd/MM/yyyy', at);
        return this;
    }

    @Override
    JSONObject toJSON() {
        JSONObject root = super.toJSON()
        root.req << jsonBuilder() {
            SearchData = [
                    SearchMode: request.searchMode,
                    HotelSearchData: request.hotelSearchData,
                    AirSearchData: {
                        def cities =  [[
                                CiaCodeList: request.origin.flightCompanies,
                                NonStop: request.origin.nonStop,
                                Origin: request.origin.code,
                                Destination: request.destination.code,
                                DepartureYear: request.origin.departureDay?.format("yyyy"),
                                DepartureMonth: request.origin.departureDay?.format("MM"),
                                DepartureDay: request.origin.departureDay?.format("dd")
                        ]]
                        if(request.destination.departureDay){
                            cities << [
                                CiaCodeList: request.destination.flightCompanies,
                                NonStop: request.destination.nonStop,
                                Origin: request.destination.code,
                                Destination: request.origin.code,
                                DepartureYear: request.destination.departureDay?.format("yyyy"),
                                DepartureMonth: request.destination.departureDay?.format("MM"),
                                DepartureDay: request.destination.departureDay?.format("dd")
                            ]
                        }
                        CityPairsRequest = cities
                        NumberADTs = request.numberOfAdults
                        NumberCHDs = request.numberOfChildren
                        SearchType = request.searchType
                        CabinFilter = request.cabinFilter
                    }
            ]
        }
        log.info(root.toString());
        return root;
    }

}
