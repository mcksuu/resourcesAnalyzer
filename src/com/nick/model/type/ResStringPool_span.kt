package com.nick.model.type

import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2017/11/29.
 */
class ResStringPool_span(var name: ResStringPool_ref, //指向样式字符串在字符串池中偏移,例如粗体样式<b>XXX</b>,则此处指向b
                         var firstChar: uint32_t, //指向应用样式的第一个字符
                         var lastChar: uint32_t) { //指向应用样式的第一个字符
    enum class End(var value: Long) {
        END(0xffffffff)
    }

    override fun toString(): String {
        return "nameRef = ${name.index.getValue()}, firstChar = ${firstChar.getValue()}, lastChar = ${lastChar.getValue()}}"
    }
}