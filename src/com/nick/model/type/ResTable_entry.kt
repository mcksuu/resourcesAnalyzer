package com.nick.model.type

import com.nick.model.unit.uint16_t

/**
 * struct ResTable_entry
 * {
 * // Number of bytes in this structure.
 * uint16_t size;
 *
 *
 * enum {
 * // If set, this is a complex entry, holding a set of name/value
 * // mappings.  It is followed by an array of ResTable_map structures.
 * FLAG_COMPLEX = 0x0001,
 * // If set, this resource has been declared public, so libraries
 * // are allowed to reference it.
 * FLAG_PUBLIC = 0x0002
 * };
 * uint16_t flags;
 *
 *
 * // Reference into ResTable_package::keyStrings identifying this entry.
 * struct ResStringPool_ref key;
 * };

 * @author i
 */
open class ResTable_entry(var size: uint16_t,
                          var flags: uint16_t,
                          var key: ResStringPool_ref?) {
    var dataStr: String? = ""
    override fun toString(): String {
        return "size = " + size.getValue() + ",flags = " + flags.getValue() + ",key = " + key!!.index.getValue() + ",str = " + dataStr
    }

    companion object {
        val FLAG_COMPLEX = 0x0001
        val FLAG_PUBLIC = 0x0002
    }

}
