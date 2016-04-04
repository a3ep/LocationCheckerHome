package net.bondar.impl
/**
 *
 */
class CoordinatesGPAUrlBuilder extends GPAUrlBuilder {
    private String latitude
    private String longitude

    CoordinatesGPAUrlBuilder(String latitude, String longitude) {
        this.latitude = latitude
        this.longitude = longitude
    }
    /**
    *
    *
    * @return
    */
    @Override
    URL build() {
        StringBuilder urlBuilder = new StringBuilder(super.mainUrlPart).append("location=${latitude},${longitude}").append("&rankby=distance").append("&types=${super.placeTypes}").append("&key=${super.gpaKey}")
        new URL(urlBuilder.toString())
    }
}
