package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t
import com.nick.model.unit.uint8_t

class Res_value(var size: uint16_t, // 大小
                var res0: uint8_t, // 保留，0
                var dataType: uint8_t, // 类型
                var data: uint32_t) { // 数据,根据不同类型来展示不同数据，这部分基本上是网上拿来的

    val typeStr: String
        get() {
            when (dataType.getValue().toInt()) {
                TYPE_NULL -> return "TYPE_NULL"
                TYPE_REFERENCE -> return "TYPE_REFERENCE"
                TYPE_ATTRIBUTE -> return "TYPE_ATTRIBUTE"
                TYPE_STRING -> return "TYPE_STRING"
                TYPE_FLOAT -> return "TYPE_FLOAT"
                TYPE_DIMENSION -> return "TYPE_DIMENSION"
                TYPE_FRACTION -> return "TYPE_FRACTION"
                TYPE_FIRST_INT -> return "TYPE_FIRST_INT"
                TYPE_INT_HEX -> return "TYPE_INT_HEX"
                TYPE_INT_BOOLEAN -> return "TYPE_INT_BOOLEAN"
                TYPE_FIRST_COLOR_INT -> return "TYPE_FIRST_COLOR_INT"
                TYPE_INT_COLOR_RGB8 -> return "TYPE_INT_COLOR_RGB8"
                TYPE_INT_COLOR_ARGB4 -> return "TYPE_INT_COLOR_ARGB4"
                TYPE_INT_COLOR_RGB4 -> return "TYPE_INT_COLOR_RGB4"
            }
            return ""
        }

    val dataStr: String
        get() {
            if (dataType.getValue().toInt() == TYPE_STRING) {
                return getStringPoolStr(data.getValue())!!
            }
            if (dataType.getValue().toInt() == TYPE_ATTRIBUTE) {
                return String.format("?%s%08X", getPackage(data.getValue()), data.getValue())
            }
            if (dataType.getValue().toInt() == TYPE_REFERENCE) {
                return String.format("@%s%08X", getPackage(data.getValue()), data.getValue())
            }
            if (dataType.getValue().toInt() == TYPE_FLOAT) {
                return java.lang.Float.intBitsToFloat(data.getValue()).toString()
            }
            if (dataType.getValue().toInt() == TYPE_INT_HEX) {
                return String.format("0x%08X", data.getValue())
            }
            if (dataType.getValue().toInt() == TYPE_INT_BOOLEAN) {
                return if (data.getValue() != 0) "true" else "false"
            }
            if (dataType.getValue().toInt() == TYPE_DIMENSION) {
                return java.lang.Float.toString(complexToFloat(data.getValue())) + DIMENSION_UNITS[data.getValue() and COMPLEX_UNIT_MASK]
            }
            if (dataType.getValue().toInt() == TYPE_FRACTION) {
                return java.lang.Float.toString(complexToFloat(data.getValue())) + FRACTION_UNITS[data.getValue() and COMPLEX_UNIT_MASK]
            }
            if (dataType.getValue() in TYPE_FIRST_COLOR_INT..TYPE_LAST_COLOR_INT) {
                return String.format("#%08X", data.getValue())
            }
            if (dataType.getValue() in TYPE_FIRST_INT..TYPE_LAST_INT) {
                return data.getValue().toString()
            }
            return String.format("<0x%X, type 0x%02X>", data.getValue(), dataType.getValue())
        }

    override fun toString(): String {
        return """size = ${size.getValue()}, sizeHexValue = ${size.getHexValue()}
        |res0 = ${res0.getValue()}, res0HexValue = ${res0.getHexValue()}
        |dataType = ${dataType.getValue()}, dataTypeHexValue = ${dataType.getHexValue()}, typeStr = $typeStr
        |data = ${data.getValue()}, dataHexValue = ${data.getHexValue()}, dataStr = $dataStr""".trimMargin()
    }

    companion object {

        val TYPE_NULL = 0x00
        val TYPE_REFERENCE = 0x01
        val TYPE_ATTRIBUTE = 0x02
        val TYPE_STRING = 0x03
        val TYPE_FLOAT = 0x04
        val TYPE_DIMENSION = 0x05
        val TYPE_FRACTION = 0x06
        val TYPE_FIRST_INT = 0x10
        val TYPE_INT_DEC = 0x10
        val TYPE_INT_HEX = 0x11
        val TYPE_INT_BOOLEAN = 0x12
        val TYPE_FIRST_COLOR_INT = 0x1c
        val TYPE_INT_COLOR_ARGB8 = 0x1c
        val TYPE_INT_COLOR_RGB8 = 0x1d
        val TYPE_INT_COLOR_ARGB4 = 0x1e
        val TYPE_INT_COLOR_RGB4 = 0x1f
        val TYPE_LAST_COLOR_INT = 0x1f
        val TYPE_LAST_INT = 0x1f

        val COMPLEX_UNIT_PX = 0
        val COMPLEX_UNIT_DIP = 1
        val COMPLEX_UNIT_SP = 2
        val COMPLEX_UNIT_PT = 3
        val COMPLEX_UNIT_IN = 4
        val COMPLEX_UNIT_MM = 5
        val COMPLEX_UNIT_SHIFT = 0
        val COMPLEX_UNIT_MASK = 15
        val COMPLEX_UNIT_FRACTION = 0
        val COMPLEX_UNIT_FRACTION_PARENT = 1
        val COMPLEX_RADIX_23p0 = 0
        val COMPLEX_RADIX_16p7 = 1
        val COMPLEX_RADIX_8p15 = 2
        val COMPLEX_RADIX_0p23 = 3
        val COMPLEX_RADIX_SHIFT = 4
        val COMPLEX_RADIX_MASK = 3
        val COMPLEX_MANTISSA_SHIFT = 8
        val COMPLEX_MANTISSA_MASK = 0xFFFFFF

        private fun getPackage(id: Int): String {
            if (id.ushr(24) == 1) {
                return "android:"
            }
            return ""
        }

        fun complexToFloat(complex: Int): Float {
            return (complex and 0xFFFFFF00.toInt()).toFloat() * RADIX_MULTS[complex shr 4 and 3]
        }

        private val RADIX_MULTS = floatArrayOf(0.00390625f, 3.051758E-005f, 1.192093E-007f, 4.656613E-010f)

        private val DIMENSION_UNITS = arrayOf("px", "dip", "sp", "pt", "in", "mm", "", "")

        private val FRACTION_UNITS = arrayOf("%", "%p", "", "", "", "", "", "")
    }

}
