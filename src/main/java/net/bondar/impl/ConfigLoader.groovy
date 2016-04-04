package net.bondar.impl

import net.bondar.exceptions.LocationCheckerException

/**
 * Loading parameters like GPAKey and placeSearchRequest
 */

class ConfigLoader {

    private def slurper = new ConfigSlurper()
    private def doc

    ConfigLoader(String configFileName){
        try{
            doc = new ConfigSlurper('development').parse(new File(configFileName).toURI().toURL())
        } catch (Exception e) {
            println(e.getMessage())
            throw new LocationCheckerException("Error while loading net.bondar.config file:\n${e.message}")
        }
    }

    /**
     * Getting required parameter
     * @param paramName - name of parameter
     * @return parameter
     */
    def loadParameter(String paramName){
        try {
            doc."${paramName}"
        } catch (Exception e){
            throw new LocationCheckerException("Param file damaged or have wrong data")
        }
    }

}
