package net.bondar.interfaces

interface JsonConverterInt {
    def toJson(def obj)
    def toObject(def json)
}
