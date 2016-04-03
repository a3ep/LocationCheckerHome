package net.bondar.impl

import net.bondar.exceptions.LocationCheckerException
import net.bondar.interfaces.JsonConverterInt

class ParamsChecker {
    private CliBuilder cli

    ParamsChecker(){
    }

    def checkParams(def params){
        def options
        cli = new CliBuilder(usage: 'Service.groovy -json ', header: 'Options:')
        cli.help('Print this message')
        cli.params(args: 4, valueSeparator: ',', argsName: 'LATITUDE,LONGITUDE,maxRequestCount,maxResultCount', 'Provide necessary params for searching')
        options = cli.parse(params)
        if(!options) throw new LocationCheckerException("")
        if(options.help){
            cli.usage()
            throw new LocationCheckerException("")
        }
        if(!options.params){
            cli.usage()
            throw new LocationCheckerException("Wrong arguments! ${params}")
        }
        if(options.params != false){
            if(!(options.params.length()>0)){
                throw new LocationCheckerException("Wrong params. Please check your input.")
            }
            return options.params
        }
    }
}
