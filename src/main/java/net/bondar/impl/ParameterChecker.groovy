package net.bondar.impl
/**
 * Verifies provided arguments.
 */
class ParameterChecker {
    private CliBuilder cli

    /**
     * Verifies provided arguments.
     *
     * @param params request parameters like latitude, longitude and max count of places
     * @return map with coordinates and count of places
     */
    def checkParams(def params) {
        def options
        cli = new CliBuilder(usage: 'APIService.groovy -p [params]', header: 'Options:')
        cli.h(longOpt: "help", 'Print this message')
        cli.p(longOpt: "param", args: 3, valueSeparator: ';' as char, argName: 'latitude, longitude, maxResultCount', 'Provide necessary params for searching')
        options = cli.parse(params)
        if (options.h || !options || !options.p) {
            cli.usage()
            System.exit(0)
        }
        ["latitude": "${options.ps[0].trim()}", "longitude": "${options.ps[1].trim()}", "count": "${options.ps[2]}"]
    }
}
