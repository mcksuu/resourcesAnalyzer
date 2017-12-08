package com.nick.model.type

import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2017/11/29.
 */
class ResStringPool_header(var header: ResChunk_header,
                           var stringCount: uint32_t, //字符串的数量
                           var styleCount: uint32_t, //字符串样式的数量
                           var flags: uint32_t, //字符串的属性,可取值包括0x000(UTF-16),0x001(字符串经过排序)、0X100(UTF-8)和他们的组合值
                           var stringsStart: uint32_t, //字符串内容块相对于其头部的距离
                           var stylesStart: uint32_t, //字符串样式块相对于其头部的距离
                           var stringOffsetArray: ResString_offset_array?,
                           var styleOffsetArray: ResStyle_offset_array?,
                           var stringStringArray: ResString_string_array?,
                           var styleStringArray: ResStyle_string_array?

) {
    // Flags.
    enum class FLAGS(var flag: Int) {
        // If set, the string index is sorted by the string values (based
        // on strcmp16()).
        SORTED_FLAG(1 shl 0),
        // String pool is encoded in UTF-8
        UTF8_FLAG(1 shl 8)
    }

    override fun toString(): String {
        val result = """$header,
            |stringCount = ${stringCount.getValue()}, stringCountHexValue = ${stringCount.getHexValue()},
            |styleCount = ${styleCount.getValue()}, styleCountHexValue = ${styleCount.getHexValue()},
            |flags = ${flags.getValue()}, flagsHexValue = ${flags.getHexValue()},
            |stringsStart = ${stringsStart.getValue()}, stringsStartCountHexValue = ${stringsStart.getHexValue()},
            |stylesStart = ${stylesStart.getValue()}, stylesStartHexValue = ${stylesStart.getHexValue()},""".trimMargin()
        return result
    }
}