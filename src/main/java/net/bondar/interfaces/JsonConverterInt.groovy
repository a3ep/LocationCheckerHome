package net.bondar.interfaces
/**
 * Interface for converting from/to JSON
 */
interface JsonConverterInt {
    def toJson(def obj)
    def toObject(def json)
}
