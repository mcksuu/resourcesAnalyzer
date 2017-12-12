package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t

/**
 */
class ResTable_map_entry(size: uint16_t, flags: uint16_t, key: ResStringPool_ref?,
                         var parent: ResTable_ref, // 指向父ResTable_map_entry的资源ID，如果没有父ResTable_map_entry，则等于0
                         var count: uint32_t // ResTable_map的数量
) : ResTable_entry(size, flags, key) {

    override fun toString(): String {
        return super.toString() + "parent:" + parent.toString() + ",count:" + count.getValue()
    }

}
