package net.bondar

import net.bondar.services.APIService

/**
 * Script for getting information about closest places on the basic of specified geographical coordinates
 */
//args = ["-p","48.4644455,35.0475775,2"] --> OK
//args = ["-h"] --> help

try {
    //
    //
    //
    def service = new APIService(/*..., ..., ...*/)
    service.search(args)
} catch (Throwable t) {
    t.printStackTrace()
}
