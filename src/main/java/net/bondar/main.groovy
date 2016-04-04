package net.bondar

import net.bondar.models.ResultObject
import net.bondar.services.MainService

/**
 * Script for getting information about closest places on the basic of specified geographical coordinates
 */
//args = ["-p","48.4644455,35.0475775,2"] --> OK
//args = ["-h"] --> help message

try{
    def service = new MainService()
    def result = service.search(args)
    println result
    println("\nOperation Completed")
    return result


}
catch (Exception e){
    println(e.message)
    println("Operation failed")
    return new ResultObject("ERROR", "${e.getMessage()}")
}
catch (Throwable t){
    t.printStackTrace()
    println("Unexpected error: ${t.getMessage()}")
    return new ResultObject("ERROR", "${t.getMessage()}")
}
