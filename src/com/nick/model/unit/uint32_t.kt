package com.nick.model.unit

/**
 * uint32_t
 * Created by KangShuai on 2017/11/29.
 */
class uint32_t(var value: Int) : basic_unit<Int>(value) {
    override fun getValue(): Int {
        return com.nick.model.type.getIntValue(relValue)
    }

    override fun getHexValue(): String {
        var toHexString = Integer.toHexString(getValue())
        if (toHexString.length < 8) {
            for (i in 1..(8 - toHexString.length)) {
                toHexString = "0" + toHexString
            }
        }
        return "0x" + toHexString
    }
}