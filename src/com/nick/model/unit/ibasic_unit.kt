package com.nick.model.unit

/**
 * ibase
 * Created by KangShuai on 2017/11/29.
 */
interface ibasic_unit<T> {
    fun getValue(): T //value
    fun getHexValue(): String // 返回16进制格式化字符串
}