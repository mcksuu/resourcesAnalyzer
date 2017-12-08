package com.nick.model.type

class ResTable_map(var name: ResTable_ref, var value: Res_value) {
    override fun toString(): String {
        return "mapEntry: " + name.toString() + ",value = " + value.toString()
    }

}
