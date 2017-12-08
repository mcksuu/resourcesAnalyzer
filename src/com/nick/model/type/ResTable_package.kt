package com.nick.model.type

import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2017/11/30.
 */
class ResTable_package(
        var header: ResChunk_header,
        // If this is a base package, its ID.  Package IDs start
        // at 1 (corresponding to the value of the package bits in a
        // resource identifier).  0 means this is not a base package.
        var id: uint32_t,
        // Actual name of this package, \0-terminated.
        var name: String,
        // Offset to a ResStringPool_header defining the resource
        // type symbol table.  If zero, this package is inheriting from
        // another base package (overriding specific values in it).
        var typeStrings: uint32_t,
        // Last index into typeStrings that is for public use by others.
        var lastPublicType: uint32_t,
        // Offset to a ResStringPool_header defining the resource
        // key symbol table.  If zero, this package is inheriting from
        // another base package (overriding specific values in it).
        var keyStrings: uint32_t,
        // Last index into keyStrings that is for public use by others.
        var lastPublicKey: uint32_t) {

    override fun toString(): String {
        val result = """$header,
                    |id = ${id.getValue()}, idHexValue = ${id.getHexValue()}
                    |name = $name
                    |typeStrings = ${typeStrings.getValue()}, typeStringsHexValue = ${typeStrings.getHexValue()}
                    |lastPublicType = ${lastPublicType.getValue()}, lastPublicTypeHexValue = ${lastPublicType.getHexValue()}
                    |keyStrings = ${keyStrings.getValue()}, keyStringsHexValue = ${keyStrings.getHexValue()}
                    |lastPublicKey = ${lastPublicKey.getValue()}, lastPublicKeyHexValue = ${lastPublicKey.getHexValue()}""".trimMargin()

        return result
    }
}