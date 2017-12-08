package com.nick.model.unit

/**
 * Created by KangShuai on 2017/11/30.
 */
class char16_t(value: Char) : basic_unit<Char>(value) {
    override fun getHexValue(): String {
        return getValue().toString()
    }
}