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
     * @param args required arguments: latitude, longitude and max place count
     * @return true if arguments right ( -90<Latitude<90, -180<Longitude<180,  )
     */
    boolean check(def args) {
        log.info("Checks input parameters\n")
        try {
            if (!args[0] || !args[1] || !args[2]) throw new ApplicationException()
            def latitude = args[0].trim().split('\\.')
            def longitude = args[1].trim().split('\\.')
            def placeCount = args[2].trim().split('\\.')

            def firstNumber = latitude[0].replace("--", "-")
            def latitudeNum = (Integer.parseInt(firstNumber))
            if (latitudeNum == 90 || latitudeNum == -90) {
                if (latitude.size() > 1 && latitude[1].length() != 0 & Integer.parseInt(latitude[1]) != 0) throw new ApplicationException()
            } else {
                if (latitude.size() > 1) Integer.parseInt(latitude[1].length() <= 7 ? latitude[1] : null)
                if (latitudeNum > 89 || latitudeNum < -89) throw new ApplicationException()
            }

            firstNumber = longitude[0].replace("--", "-")
            def longitudeNum = Integer.parseInt(firstNumber)
            if (longitudeNum == 180 || longitudeNum == -180) {
                if (longitude.size() > 1 && longitude[1].length() != 0 & Integer.parseInt(longitude[1]) != 0) throw new ApplicationException()
            } else {
                if (longitude.size() > 1) Integer.parseInt(longitude[1].length() <= 7 ? longitude[1] : null)
                if (longitudeNum > 179 || longitudeNum < -179) throw new ApplicationException()
            }

            firstNumber = placeCount[0]
            def placeCountNum = Integer.parseInt(firstNumber)
            if (placeCountNum < 0 || placeCountNum > 40) throw new ApplicationException()
            int i = placeCount.size()
            if (placeCount.size() > 1 && placeCount[1].length() != 0 & Integer.parseInt(placeCount[1] != 0)) throw new ApplicationException()
            true
        } catch (ApplicationException | NumberFormatException e) {
            throw new ApplicationException("Wrong format of arguments. Please check your arguments. Example --38.453,46.455,3");
        }
    }
}
