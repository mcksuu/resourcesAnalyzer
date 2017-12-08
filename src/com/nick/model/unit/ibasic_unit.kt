package com.nick.model.unit

/**
 * Created by KangShuai on 2017/11/29.
 */
interface ibasic_unit<T> {
    fun getValue(): T
    fun getHexValue(): String
}