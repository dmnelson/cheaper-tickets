package mn.david.cheapertickets.request

import net.sf.json.JSONObject

/**
 * User: David
 * Date: 16/07/11
 * Time: 22:57
 */
class SearchRequest extends Request{
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



}
//{"req":{"PointOfSale":"SUBMARINO","SearchData":{"SearchMode":1,"AirSearchData":{"CityPairsRequest":[{"CiaCodeList":[],"NonStop":true,"Origin":"POA","Destination":"BHZ","DepartureYear":"2011","DepartureMonth":"07","DepartureDay":"22","StartTime":0,"EndTime":2},{"CiaCodeList":[],"NonStop":true,"Origin":"BHZ","Destination":"POA","DepartureYear":"2011","DepartureMonth":"07","DepartureDay":"24","StartTime":0,"EndTime":2}],"NumberADTs":"1","NumberCHDs":"0","NumberINFs":"1","SearchType":1,"CabinFilter":1},"HotelSearchData":null},"UserBrowser":"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30"}}
