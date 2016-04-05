package net.bondar.interfaces
/**
 * Converts objects from/to JSON.
 */
interface JSONConverter {

    /**
     * Converts Object to JSON.
     *
     * @param obj object that needs to convert
     * @return converted JSON
     */
    Object toJson(Object obj)

    /**
     * Converts JSON to Object.
     *
     * @param json json string that needs to convert
     * @return converted object
     */
    Object toObject(InputStreamReader json)
}
