package com.nick.model.unit

import com.nick.model.type.getShortValue

/**
 * uint16_t
 * Created by KangShuai on 2017/11/29.
 */
class uint16_t(value: Short) : basic_unit<Short>(value) {
    override fun getValue(): Short {
        return getShortValue(relValue)
    }

    override fun getHexValue(): String {
        var toHexString = Integer.toHexString(com.nick.model.type.getShortValue(relValue).toInt())
        if (toHexString.length < 4) {
            for (i in 1..4 - toHexString.length) {
                toHexString = "0" + toHexString
            }
        }
        return "0x" + toHexString
    }

}