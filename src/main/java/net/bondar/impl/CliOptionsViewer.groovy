package net.bondar.impl

import net.bondar.exceptions.ApplicationException
import net.bondar.interfaces.OptionViewer

/**
 * Views script run options.
 */
class CliOptionsViewer implements OptionViewer {
    private def options

/**
 * Builds script run options output and views options if incorrect parameters were entered.
 *
 * @param args user input arguments: option parameter, geographical coordinates (latitude and longitude) and max place count
 * @return parsed user arguments for verifying
 * @throws ApplicationException
 */
    def buildOptions(def args) {
        CliBuilder cli = new CliBuilder(usage: 'APIService.groovy -p [params]', header: 'Options:')
        cli.h(longOpt: "help", 'Print this message')
        cli.p(longOpt: "param", args: 3, valueSeparator: ';', argName: 'latitude, longitude, maxResultCount', 'Searching location with given coordinates. Negative numbers looks like --34.586')
        options = cli.parse(args)
        if (!options) {
            throw new ApplicationException("Error empty arguments. Please check your input.")
        } else if (options.h) {
            cli.usage()
            System.exit(0)
        } else if (!options.p) {
            cli.usage()
            throw new ApplicationException("Wrong parameters or options. Please check your input.")
        }
        options.ps
    }
}