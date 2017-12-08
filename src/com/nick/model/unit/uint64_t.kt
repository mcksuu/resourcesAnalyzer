package com.nick.model.unit

/**
 * uint32_t
 * Created by KangShuai on 2017/11/29.
 */
class uint64_t(var value: Long) : basic_unit<Long>(value) {
    override fun getValue(): Long {
        return com.nick.model.type.getLongValue(relValue)
    }

    override fun getHexValue(): String {
        var toHexString = java.lang.Long.toHexString(getValue())
        if (toHexString.length < 16) {
            for (i in 1..(8 - toHexString.length)) {
                toHexString = "0" + toHexString
            }
        }
        return "0x" + toHexString
    }
}