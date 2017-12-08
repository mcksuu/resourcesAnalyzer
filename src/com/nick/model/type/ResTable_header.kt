package com.nick.model.type

import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2017/11/29.
 */
class ResTable_header(var header: ResChunk_header, var packageCount: uint32_t) {
    override fun toString(): String {

        return header.toString() + ", packagerCount = ${packageCount.getValue()}, packagerCountHexValue = ${packageCount.getHexValue()}"
    }
}