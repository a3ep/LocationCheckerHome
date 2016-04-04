package net.bondar

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.impl.*
import net.bondar.interfaces.APIConnection
import net.bondar.interfaces.AbstractUrlBuilderFactory
import net.bondar.interfaces.JSONConverter
import net.bondar.interfaces.ObjectConverter
import net.bondar.services.APIService

/**
 * Script for getting information about closest places on the basic of specified geographical coordinates
 */
//args = ["-p","48.4644455,35.0475775,2"] --> OK
//args = ["-h"] --> help

try {
    AbstractUrlBuilderFactory urlBuilderFactory = new GPAUrlBuilderFactory()
    APIConnection apiConnection = new GooglePlacesAPI()
    ObjectConverter objectConverter = new GPAObjectConverter()
    JSONConverter jsonConverter = new GroovyJSONConverter(new JsonBuilder(), new JsonSlurper())
    ParameterChecker parameterChecker = new ParameterChecker()

    def service = new APIService(urlBuilderFactory, apiConnection, objectConverter, jsonConverter, parameterChecker)
    println(service.search(parameterChecker.checkParams(args.size() > 0 ? args : ["-h"])))
} catch (Throwable t) {
    t.printStackTrace()
}
