package com.nick.model

/**
 * Created by KangShuai on 2017/12/6.
 */
class Config {
    companion object {
        val RESCHUNK_HEADER_SIZE = 8
        val RESTABLE_HEADER_SIZE = RESCHUNK_HEADER_SIZE + 4
        val RESTABLE_MAP_SIZE = 12
        val RES_VALUE_SIZE = 8
    }
}