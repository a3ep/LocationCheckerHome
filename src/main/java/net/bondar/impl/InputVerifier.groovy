package net.bondar.impl

import net.bondar.exceptions.ApplicationException
import net.bondar.interfaces.OptionViewer
import net.bondar.interfaces.ParameterVerifier
import net.bondar.models.ResultObject

/**
 * Builds run script options and verifies user input arguments.
 *
 */
class InputVerifier {
    private OptionViewer optionViewer
    private ParameterVerifier parameterVerifier

    InputVerifier(OptionViewer optionViewer, ParameterVerifier parameterVerifier) {
        this.optionViewer = optionViewer
        this.parameterVerifier = parameterVerifier
    }

    /**
     * Builds run script options and verifies user input arguments.
     *
     * @param args user input arguments: option parameter, geographical coordinates (latitude and longitude) and max place count
     * @return verifying user input arguments or ResultObject with error message if verifying failed
     * @throws ApplicationException
     */
    def verifyInput(def args) {
        args = optionViewer.buildOptions(args)
        try {
            if (parameterVerifier.verify(args)) {
                ["latitude": "${args[0].trim()}", "longitude": "${args[1].trim()}", "count": "${args[2]}"]
            }
        } catch (ApplicationException e) {
            return new ResultObject(e.message)
        }
    }
}