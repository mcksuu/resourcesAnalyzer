package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t
import com.nick.model.unit.uint8_t

/**
 * struct ResTable_typeSpec
 * {
 * struct ResChunk_header header;

 * // The type identifier this chunk is holding.  Type IDs start
 * // at 1 (corresponding to the value of the type bits in a
 * // resource identifier).  0 is invalid.
 * uint8_t id;

 * // Must be 0.
 * uint8_t res0;
 * // Must be 0.
 * uint16_t res1;

 * // Number of uint32_t entry configuration masks that follow.
 * uint32_t entryCount;

 * enum {
 * // Additional flag indicating an entry is public.
 * SPEC_PUBLIC = 0x40000000
 * };
 * };
 * @author i
 */
class ResTable_typeSpec(var header: ResChunk_header,
                        var id: uint8_t,
                        var res0: uint8_t,
                        var res1: uint16_t,
                        var entryCount: uint32_t) {

    override fun toString(): String {
        return """header = $header ,
                |id = ${id.getValue()}, idHexValue = ${id.getHexValue()},
                |res0 =${res0.getValue()} ,res1 = ${res1.getValue()} ,
                |entryCount = ${entryCount.getValue()}, entryCountHexValue = ${entryCount.getHexValue()}""".trimMargin()
    }

    companion object {

        val SPEC_PUBLIC = 0x40000000
    }

}
