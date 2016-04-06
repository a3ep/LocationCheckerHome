package net.bondar.interfaces

import net.bondar.models.InputObject

/**
 * Processes creation of temp result object.
 */
interface Processor {

    /**
     * Processes creation of temp result object.
     *
     * @return complete temp result object
     */
    InputObject process()
}