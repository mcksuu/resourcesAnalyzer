package com.nick.model.type

import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2017/12/6.
 */
class Res_entry_array(var entryArray: Array<uint32_t>) {
    override fun toString(): String {
        var result: String = ""
        entryArray.forEach {
            result += it.getValue().toString() + " "
        }
        return result
    }
}