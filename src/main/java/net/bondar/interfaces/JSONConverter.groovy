package net.bondar.interfaces
/**
 * Converts objects from/to JSON.
 */
interface JSONConverter {
    Object toJson(Object obj)

    Object toObject(InputStreamReader json)
}
