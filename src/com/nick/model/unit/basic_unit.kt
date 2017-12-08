package com.nick.model.unit

/**
 * Created by KangShuai on 2017/11/29.
 */
abstract class basic_unit<T>(protected var relValue: T) : ibasic_unit<T> {

    override fun getValue(): T {
        return relValue
    }

}