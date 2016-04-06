package net.bondar.impl

import groovy.util.logging.Log4j
import net.bondar.exceptions.ApplicationException
import org.apache.log4j.Level

/**
 * Loads parameters application parameters.
 */
@Log4j
class ParameterLoader {
    private def slurper = new ConfigSlurper()
    private def doc

    ParameterLoader(String configFileName) {
        try {
            doc = slurper.parse(new File(configFileName).toURI().toURL())
        } catch (FileNotFoundException e) {
            log.setLevel(Level.DEBUG)
            log.debug("Error while loading configuration file.")
            throw new ApplicationException("Error while loading net.bondar.config file:\n${e.message}")
        }
    }

    /**
     * Loads required parameters from configuration file.
     *
     * @param paramName name of parameter required
     * @return value of required parameter
     */
    String loadParameter(String paramName) {
        doc."${paramName}"
    }
}
