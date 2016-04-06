package net.bondar.impl

import groovy.util.logging.Log4j
import net.bondar.exceptions.LocationCheckerException
import net.bondar.interfaces.ObjectChecker
import org.apache.log4j.Level

/**
 * Checks response object status.
 */
@Log4j
class GPAObjectChecker implements ObjectChecker {

    /**
     * Checks response object status.
     *
     * @param responseObject object with location information from Google Places API.
     * @return true , if status is 'OK' or 'ZERO_RESULT, else - false
     * @throws LocationCheckerException
     */
    boolean check(Object responseObject) {
        if (responseObject.status != 'OK' && responseObject.status != 'ZERO_RESULTS') {
            log.setLevel(Level.DEBUG)
            log.debug("Error while checking GPA response status.")
            throw new LocationCheckerException("Error while checking GPA response status.\nResponce status --> ${responseObject.status}");
        }
        (responseObject.status == 'OK' || responseObject.status == 'ZERO_RESULTS')
    }
}
