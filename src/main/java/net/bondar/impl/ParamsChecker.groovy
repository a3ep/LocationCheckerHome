package net.bondar.impl

import net.bondar.interfaces.JsonConverterInt

class ParamsChecker {
    private def params
    private def cli
    private JsonConverterInt jConverter

    ParamsChecker(JsonConverterInt jConverter){
        this.params = params
        this.jConverter = jConverter
    }

    def parseParams(def params){
        params.size()>0?jConverter.toObject(params):["-help"]
    }

    def checkParams(def params){
        cli = new CliBuilder(usage: 'Service.groovy -json ', header: 'Options:')
        cli.help('Print this message')
        cli.json(args: 4, valueSeparator: ',', argsName: 'LATITUDE,LONGITUDE,maxRequestCount,maxResultCount', 'Provide necessary params for searching')




    }
}
