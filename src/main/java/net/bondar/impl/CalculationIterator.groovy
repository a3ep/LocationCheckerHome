package net.bondar.impl

import net.bondar.exceptions.ApplicationException
import net.bondar.exceptions.LocationCheckerException
import net.bondar.interfaces.APIConnection
import net.bondar.interfaces.JSONConverter
import net.bondar.interfaces.ObjectChecker
import net.bondar.models.Place
import net.bondar.models.PlaceKeeper

/**
 * Iterates temp result object creation process.
 */
class CalculationIterator {
    private PlaceKeeper keeper = new PlaceKeeper()
    private APIConnection apiConnection
    private ObjectChecker objectChecker
    private JSONConverter jsonConverter
    private int placeCount
    private String latitude
    private String longitude


    CalculationIterator(String latitude, String longitude, int placeCount, APIConnection apiConnection, ObjectChecker objectChecker, JSONConverter jsonConverter) {
        this.latitude = latitude
        this.longitude = longitude
        this.placeCount = placeCount
        this.apiConnection = apiConnection
        this.objectChecker = objectChecker
        this.jsonConverter = jsonConverter
    }

    /**
     * Checks that the size of place list, on the basis of which temp result object will be created, less then requested place count.
     *
     * @return true , if size < count, else - false
     */
    boolean hasNext() {
        keeper.places.size() < placeCount
    }

    /**
     * Returns next place list from Google Places API response.
     *
     * @return the list of places
     */
    List<Place> next() {
        URL url = new GPAUrlBuilderFactory().createUrlBuilder(latitude, longitude, keeper.placeToken).build()
        try {
            Object response = jsonConverter.toObject(apiConnection.getInputStream(url))
            if (objectChecker.check(response)) {
                keeper.placeToken = response.next_page_token
                List<Place> placeList = new ArrayList<>()
                response.results.each {
                    placeList.add(new Place("${it.name.value}", "${it.geometry.location.lat}", "${it.geometry.location.lng}"))
                }
                placeList
            }
        } catch (ApplicationException e) {
            throw new LocationCheckerException(e.message)
        }
    }

    /**
     * Updates the keeper's list of places.
     *
     * @return keeper with updated places
     */
    PlaceKeeper updateKeeper() {
        keeper.places.addAll(next())
        return keeper
    }
}
