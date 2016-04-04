package net.bondar.impl

import net.bondar.exceptions.LocationCheckerException
import net.bondar.interfaces.JsonConverterInt

/**
 * Verifies provided arguments
 */
class ParamsChecker {
    private CliBuilder cli

    ParamsChecker() {
    }
/**
 * Method which verifying provided arguments
 * @param params - user request
 * @return coordinates and count of places
 */
    def checkParams(def params) {
        def options
        cli = new CliBuilder(usage: 'APIService.groovy -p [params]', header: 'Options:')
        cli.h(longOpt: "help", 'Print this message')
        cli.p(longOpt: "param", args: 3, valueSeparator: ';' as char, argName: 'Latitude, longitude, maxResultCount', 'Provide necessary params for searching')
        options = cli.parse(params)
        if (!options) throw new LocationCheckerException()
        if (options.help) {
            cli.usage()
            throw new LocationCheckerException()
        }
        if (!options.param) {
            cli.usage()
            throw new LocationCheckerException("Wrong arguments! ${params}")
        }
        if (options.param != false) {
            if (!(options.param.length() > 0)) {
                throw new LocationCheckerException("Wrong params. Please check your input.")
            }
            return ["latitude": "${options.params[0].trim()}", "longitude": "${options.params[1].trim()}", "count": "${options.params[2]}"]
        }
    }
}
