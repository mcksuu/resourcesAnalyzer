package com.nick.model.unit

/**uint8_t
 * Created by KangShuai on 2017/11/29.
 */
class uint8_t(value: Byte) : basic_unit<Byte>(value) {
    override fun getHexValue(): String {
        return Integer.toHexString(relValue.toInt())
    }
}