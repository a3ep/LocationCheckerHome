package net.bondar

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.exceptions.LocationCheckerException
import net.bondar.utils.DistanceCalculator

class MainWorker {
//    private final Script = Config
    /*Main Google Places API (GPAPI) URL part */
    private static final String mainUrl = "https://maps.googleapis.com/maps/api/place/search/json?"
    /*Parameters for building URL*/
    private String latitude = ""
    private String longitude = ""
    private String radius = "50"
    /**Google Places API key*/
    private static final String gpaKey = 'AIzaSyClbZYj73b-XfcjU4i0sQ1m2C858x46auI'
    /*Complete GPAPI URL*/
    private URL url

    MainWorker(){
        /*Reading latitude and longitude from console*/
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        try{
            println("Set latitude:")
            latitude = reader.readLine()
            println("Set longitude:")
            longitude =reader.readLine()
        }
        catch (Exception e){
            e.printStackTrace()
        }finally{
            reader.close()
        }

//        ConfigLoader loader = new ConfigLoader(CONFIG_FILE)

        /*Building GPAPI URL*/
        StringBuilder urlBuilder = new StringBuilder(mainUrl)
        urlBuilder.append("location=" + latitude + "," + longitude)
        urlBuilder.append("&radius=" + radius)
        urlBuilder.append("&key=" + gpaKey)
        url= new URL(urlBuilder.toString())
    }

    public static void main(String[] args) {
        MainWorker worker = new MainWorker()
        def responseObject = jsonToObject(getLocationInfo(worker.url))
        println(responseObject)
        println(responseObject.status)

//        if(response.status!= 'OK' || response.status!= "ZERO_RESULTS") throw new LocationCheckerException("Error while searching places: \n${response.status}");

        /*Receiving list of places from response object*/
        List<Place> placeList = new ArrayList<>()
        responseObject.results.each{

            placeList.add(new Place("${it.name.value}", "${it.geometry.location.lat}", "${it.geometry.location.lng}"))
        }
        println("Closest places:\n"+placeList+"\n")

        /*Converting result to JSON object*/
        JsonBuilder jBuilder = new JsonBuilder()
        def resultToJson = jBuilder.call(filter(placeList, worker.latitude, worker.longitude, null))

        println("Result to JSON:\n"+resultToJson)
    }
    /**
     * Creates connection and receiving GPAPI information about location
     * @param url - GPAPI URL
     * @return response
     */
    def static getLocationInfo(def url){
        def connect = (HttpURLConnection) url.openConnection()
        def responseCode = connect.getResponseCode()
        println responseCode
        if(responseCode != 200) throw new LocationCheckerException("Wrong responce code:  ${responseCode}")
        return new InputStreamReader(connect.getInputStream())
    }
    /**
     * Parsing response JSON to Object
     * @param response - GPAPI response
     * @return response object
     */
    def static jsonToObject(def response){
        def responseObject
        JsonSlurper jSlurper = new JsonSlurper()
        try{
            responseObject = jSlurper.parse(response)
        }
        catch (Exception e){
            throw new LocationCheckerException("Error while reading data from Google Places Api: \n${e.getMessage()}")
        }
        return responseObject
    }
    /**
     * Finds the closest places on the basis of specified geographical coordinates
     * @param placeList - list of places
     * @param latitude - specified latitude
     * @param longitude - specified longitude
     * @return - place object or list of places
     */
    def static filter(def placeList, def latitude, def longitude, def minD){
        def result
        def number=0
        if (placeList.size()<2){
            result = placeList.get(number)
            println result
        }
        def targetLat = placeList.get(0).latitude
        def targetLng = placeList.get(0).longitude
        DistanceCalculator distCalc = new DistanceCalculator(latitude, longitude)
        double minDistance = distCalc.calculateDistance(targetLat, targetLng)
        ArrayList<Place> sameLocations = new ArrayList()
        ArrayList<Place> closestLocation = new ArrayList()

        for (int i = 1; i < placeList.size(); i++) {
            def currentLat = placeList.get(i).latitude
            def currentLng = placeList.get(i).longitude
            double targetDistance = distCalc.calculateDistance(currentLat, currentLng)
            if(targetDistance<=>0d==0){
                sameLocations.add(placeList.get(i))
                continue
            }
            if (targetDistance <=> minDistance == -1) {
                minDistance = targetDistance
                number=i
            }
        }
        if(placeList.size()<20 && sameLocations.size()!=0) return sameLocations
        else if(placeList.size()<20 && minD!=null){
            closestLocation.add(placeList.remove(number))
            placeList.each{
                if(distCalc.calculateDistance(it.latitude, it.longitude)<=> minDistance == 0){
                    closestLocation.add(it)
                }
            }
            return closestLocation
        }
        minD=minDistance
        StringBuilder urlBuilder = new StringBuilder(mainUrl)
        urlBuilder.append("location="+latitude+","+longitude)
        urlBuilder.append("&radius="+minDistance)
        urlBuilder.append("&key="+gpaKey)
        def responseObject = jsonToObject(getLocationInfo(new URL(urlBuilder.toString())))
        def newPlaceList = new ArrayList()
        responseObject.results.each{
            newPlaceList.add(new Place("${it.name.value}", "${it.geometry.location.lat}", "${it.geometry.location.lng}"))
        }
        result= filter(newPlaceList,latitude, longitude, minD)
        return result
    }

}
