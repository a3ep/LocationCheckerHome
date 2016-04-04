package net.bondar.impl

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.models.Place
import net.bondar.models.PlaceKeeper

/**
 *
 */
class PlaceIterator implements Iterator {
    private PlaceKeeper keeper = new PlaceKeeper()
    private int placeCount
    private String latitude
    private String longitude


    PlaceIterator(String latitude, String longitude, int placeCount) {
        this.latitude = latitude
        this.longitude = longitude
        this.placeCount = placeCount
    }

    @Override
    boolean hasNext() {
        keeper.places.size() < placeCount
    }

    @Override
    Object next() {
        URL url = new GPAUrlBuilderFactory().createUrlBuilder(latitude, longitude, keeper.placeToken).build()
        Object response = new GroovyJSONConverter(new JsonBuilder(), new JsonSlurper()).toObject(new GooglePlacesAPI().getInputStream(url))
        keeper.placeToken = response.next_page_token
        response.results.each {
            keeper.places.add(new Place("${it.name.value}", "${it.geometry.location.lat}", "${it.geometry.location.lng}"))
        }
        return keeper
    }
}
