package mn.david.cheapertickets.search.engine.submarino.request

import mn.david.cheapertickets.search.SearchQuery
import net.sf.json.JSONObject

/**
 * User: David
 * Date: 16/07/11
 * Time: 22:57
 */
class SearchRequest extends Request {
    int searchMode = 1;
    int numberOfAdults = 1;
    int numberOfChildren = 0;
    int searchType = 1;
    def cabinFilter = null;
    def hotelSearchData = null;
    final SearchCity origin = new SearchCity();
    final SearchCity destination = new SearchCity();

    class SearchCity {
        String code;
        String flightCompanies = [];
        boolean nonStop = false;
        Date departureDay;
    }

    SearchRequest(SearchQuery searchQuery) {
        origin.code = searchQuery.origin.toString();
        origin.departureDay = searchQuery.departureDate;
        destination.code = searchQuery.destination.toString();
    }

    @Override
    JSONObject toJSON() {
        JSONObject root = super.toJSON()
        root.req << jsonBuilder() {
            SearchData = [
                    SearchMode: this.searchMode,
                    HotelSearchData: this.hotelSearchData,
                    AirSearchData: {
                        def cities = [[
                                CiaCodeList: this.origin.flightCompanies,
                                NonStop: this.origin.nonStop,
                                Origin: this.origin.code,
                                Destination: this.destination.code,
                                DepartureYear: this.origin.departureDay?.format("yyyy"),
                                DepartureMonth: this.origin.departureDay?.format("MM"),
                                DepartureDay: this.origin.departureDay?.format("dd")
                        ]]
                        if (this.destination.departureDay) {
                            cities << [
                                    CiaCodeList: this.destination.flightCompanies,
                                    NonStop: this.destination.nonStop,
                                    Origin: this.destination.code,
                                    Destination: this.origin.code,
                                    DepartureYear: this.destination.departureDay?.format("yyyy"),
                                    DepartureMonth: this.destination.departureDay?.format("MM"),
                                    DepartureDay: this.destination.departureDay?.format("dd")
                            ]
                        }
                        CityPairsRequest = cities
                        NumberADTs = this.numberOfAdults
                        NumberCHDs = this.numberOfChildren
                        SearchType = this.searchType
                        CabinFilter = this.cabinFilter
                    }
            ]
        }
        return root;

    }
}
//{"req":{"PointOfSale":"SUBMARINO","SearchData":{"SearchMode":1,"AirSearchData":{"CityPairsRequest":[{"CiaCodeList":[],"NonStop":true,"Origin":"POA","City":"BHZ","DepartureYear":"2011","DepartureMonth":"07","DepartureDay":"22","StartTime":0,"EndTime":2},{"CiaCodeList":[],"NonStop":true,"Origin":"BHZ","City":"POA","DepartureYear":"2011","DepartureMonth":"07","DepartureDay":"24","StartTime":0,"EndTime":2}],"NumberADTs":"1","NumberCHDs":"0","NumberINFs":"1","SearchType":1,"CabinFilter":1},"HotelSearchData":null},"UserBrowser":"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30"}}
