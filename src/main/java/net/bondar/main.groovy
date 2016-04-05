package net.bondar

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.impl.*
import net.bondar.interfaces.APIConnection
import net.bondar.interfaces.AbstractProcessorFactory
import net.bondar.interfaces.AbstractUrlBuilderFactory
import net.bondar.interfaces.JSONConverter
import net.bondar.interfaces.ObjectChecker
import net.bondar.services.APIService

/**
 * Script for getting information about closest places on the basic of specified geographical coordinates
 */
//args = ["-p","48.4644455,35.0475775,2"] /*--> OK*/
//args = ["-h"] /*--> help*/

try {
    AbstractUrlBuilderFactory urlBuilderFactory = new GPAUrlBuilderFactory()
    AbstractProcessorFactory processorFactory = new APIProcessorFactory()
    APIConnection apiConnection = new GooglePlacesAPI()
    ObjectChecker objectChecker = new GPAObjectChecker()
    JSONConverter jsonConverter = new GroovyJSONConverter(new JsonBuilder(), new JsonSlurper())
    ParameterChecker parameterChecker = new ParameterChecker()

    def service = new APIService(urlBuilderFactory, processorFactory, apiConnection, objectChecker, jsonConverter, parameterChecker)
    println service.search(service.checkInput(args.size() > 0 ? args : ["-h"]))
} catch (Throwable t) {
    t.printStackTrace()
}
