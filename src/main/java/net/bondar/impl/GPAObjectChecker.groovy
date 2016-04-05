package net.bondar.impl

import groovy.util.logging.Log
import net.bondar.exceptions.LocationCheckerException
import net.bondar.interfaces.ObjectChecker

/**
 * Checks response object status.
 */
@Log
class GPAObjectChecker implements ObjectChecker {

    /**
     * Checks response object status.
     *
     * @param responseObject object with location information from Google Places API.
     * @return true , if status is 'OK' or 'ZERO_RESULT, else - false
     */
    boolean check(Object responseObject) {
        if (responseObject.status != 'OK' && responseObject.status != 'ZERO_RESULTS') {
            log.info("Error while checking GPA response status.")
            throw new LocationCheckerException("Error while checking GPA response status.\nResponce status --> ${responseObject.status}");
        }
        (responseObject.status == 'OK' || responseObject.status == 'ZERO_RESULTS')
    }
}
