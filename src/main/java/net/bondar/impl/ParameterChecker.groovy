package net.bondar.impl

import groovy.util.logging.Log
import net.bondar.exceptions.ApplicationException

/**
 * Verifies provided arguments.
 */
@Log
class ParameterChecker {

    /**
     * Verifies provided arguments.
     *
     * @param coordinates geographical coordinates: latitude and longitude
     * @return true if coordinates right ( -90<Latitude<90, -180<Longitude<180 )
     */
    boolean check(def coordinates) {
        log.info("Checks input parameters\n")
        try {
            if (coordinates.latitude == null || coordinates.longitude == null) throw new ApplicationException()
            def latitude = coordinates.latitude.trim().split('\\.')
            def longitude = coordinates.longitude.trim().split('\\.')
            def numberFirst = latitude[0].replace("--", "-")
            def latitudeNum = (Integer.parseInt(numberFirst))
            if (latitudeNum == 90 || latitudeNum == -90) {
                if (latitude.size() > 1 && latitude[1].length() != 0 & Integer.parseInt(latitude[1]) != 0) throw new ApplicationException()
            } else {
                if (latitude.size() > 1) Integer.parseInt(latitude[1].length() <= 7 ? latitude[1] : null)
                if (latitudeNum > 89 || latitudeNum < -89) throw new ApplicationException()
            }

            numberFirst = longitude[0].replace("--", "-")
            def longitudeNum = Integer.parseInt(numberFirst)
            if (longitudeNum == 180 || longitudeNum == -180) {
                if (longitude.size() > 1 && longitude[1].length() != 0 && Integer.parseInt(longitude[1]) != 0) throw new ApplicationException()
            } else {
                if (longitude.size() > 1) Integer.parseInt(longitude[1].length() <= 7 ? longitude[1] : null)
                if (longitudeNum > 179 || longitudeNum < -179) throw new ApplicationException()
            }
            true
        } catch (ApplicationException e) {
            throw new ApplicationException("Wrong format of coordinates. Please check your coordinates. Example --38.453,46.455");
        }
    }
}
