package net.bondar.impl

import groovy.util.logging.Log4j
import net.bondar.exceptions.ApplicationException
import net.bondar.exceptions.LocationCheckerException
import net.bondar.interfaces.APIConnection
import net.bondar.interfaces.JSONConverter
import net.bondar.interfaces.ObjectChecker
import net.bondar.models.Place
import org.apache.log4j.Level

/**
 * Iterates input object creation process.
 */
@Log4j
class CalculationIterator {

    private APIConnection apiConnection
    private ObjectChecker objectChecker
    private JSONConverter jsonConverter
    private int placeCount
    private String latitude
    private String longitude
    private List<Place> placeList = new ArrayList<>()
    private String pageToken

    CalculationIterator(String latitude, String longitude, int placeCount, APIConnection apiConnection, ObjectChecker objectChecker, JSONConverter jsonConverter) {
        this.latitude = latitude
        this.longitude = longitude
        this.placeCount = placeCount
        this.apiConnection = apiConnection
        this.objectChecker = objectChecker
        this.jsonConverter = jsonConverter
    }

    /**
     * Checks that the size of place list, on the basis of which input object will be created, less then requested place count.
     *
     * @return true , if size < count, else - false
     */
    boolean hasNext() {
        placeList.size() < placeCount
    }

    /**
     * Returns next place list from Google Places API response.
     *
     * @return the list of places
     * @throws LocationCheckerException
     */
    List<Place> next() {
        try {
            URL url = new GPAUrlBuilderFactory().createUrlBuilder(latitude, longitude, pageToken).build()
            if (url.toString().contains("pagetoken")) {
                Thread.sleep(2000)
            }
            Object response = jsonConverter.toObject(apiConnection.getInputStream(url))
            if (objectChecker.check(response)) {
                pageToken = response.next_page_token
                response.results.each {
                    placeList.add(new Place("${it.name.value}", "${it.geometry.location.lat}", "${it.geometry.location.lng}"))
                }
                placeList
            }
        } catch (ApplicationException e) {
            log.setLevel(Level.DEBUG)
            log.debug("Error while using iterator.")
            throw new LocationCheckerException(e.message)
        }
    }
}
