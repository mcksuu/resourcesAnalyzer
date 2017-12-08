package com.nick.model.type

import com.nick.model.unit.uint32_t

/**
 * struct ResTable_ref
 * {
 * uint32_t ident;
 * };

 * @author i
 */
class ResTable_ref(var ident: uint32_t) {

    override fun toString(): String {
        return "ident = ${ident.getHexValue()}"
    }

}
