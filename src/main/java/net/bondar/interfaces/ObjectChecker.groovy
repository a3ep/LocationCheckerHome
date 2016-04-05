package net.bondar.interfaces
/**
 * Checks response object status.
 */
interface ObjectChecker {

    /**
     * Checks response object status.
     *
     * @param responseObject object information from API.
     * @return true or false
     */
    boolean check(Object object)
}
