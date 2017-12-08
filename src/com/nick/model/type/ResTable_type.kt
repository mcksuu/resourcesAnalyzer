package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t
import com.nick.model.unit.uint8_t

/**
 * struct ResTable_type
 * {
 * struct ResChunk_header header;

 * enum {
 * NO_ENTRY = 0xFFFFFFFF
 * };

 * // The type identifier this chunk is holding.  Type IDs start
 * // at 1 (corresponding to the value of the type bits in a
 * // resource identifier).  0 is invalid.
 * uint8_t id;

 * // Must be 0.
 * uint8_t res0;
 * // Must be 0.
 * uint16_t res1;

 * // Number of uint32_t entry indices that follow.
 * uint32_t entryCount;

 * // Offset from header where ResTable_entry data starts.
 * uint32_t entriesStart;

 * // Configuration this collection of entries is designed for.
 * ResTable_config config;
 * };
 * @author i
 */
class ResTable_type(var header: ResChunk_header,
                    var id: uint8_t,
                    var res0: uint8_t,
                    var res1: uint16_t,
                    var entryCount: uint32_t,
                    var entriesStart: uint32_t,
                    var resConfig: ResTable_config) {

    override fun toString(): String {
        return """header:, $header ,
                |id = ${id.getValue()}, idHexValue = ${id.getHexValue()},
                |res0 = ${res0.getValue()},res1 = ${res1.getValue()},
                |entryCount = ${entryCount.getValue()}, entryCountHexValue = ${entryCount.getHexValue()},
                |entriesStart = ${entriesStart.getValue()}, entriesStartHexValue = ${entriesStart.getHexValue()}
                |resConfig = $resConfig""".trimMargin()
    }

    companion object {
        val NO_ENTRY = 0xFFFFFFFF.toInt()
    }

}
