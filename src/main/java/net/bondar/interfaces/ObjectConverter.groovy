package net.bondar.interfaces

import net.bondar.models.TempResultObject

/**
 * Interface for parsing data
 */
interface ObjectConverter {
    TempResultObject convert(Object object)
}
