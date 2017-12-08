package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t

/**
 * ResChunk_header
 * Created by KangShuai on 2017/11/29.
 */
class ResChunk_header(var type: uint16_t, //当前这个chunk的类型
                      var headerSize: uint16_t, //当前这个chunk的头部大小
                      var size: uint32_t) {
    //当前这个chunk的大小
    enum class ChunkType(var type: Int) {
        RES_NULL_TYPE(0x0000),
        RES_STRING_POOL_TYPE(0x0001),
        RES_TABLE_TYPE(0x0002),
        RES_XML_TYPE(0x0003),
        RES_XML_FIRST_CHUNK_TYPE(0x0100),
        RES_XML_START_NAMESPACE_TYPE(0x0100),
        RES_XML_END_NAMESPACE_TYPE(0x0101),
        RES_XML_START_ELEMENT_TYPE(0x0102),
        RES_XML_END_ELEMENT_TYPE(0x0103),
        RES_XML_CDATA_TYPE(0x0104),
        RES_XML_LAST_CHUNK_TYPE(0x017f),
        RES_XML_RESOURCE_MAP_TYPE(0x0180),
        RES_TABLE_PACKAGE_TYPE(0x0200),
        RES_TABLE_TYPE_TYPE(0x0201),
        RES_TABLE_TYPE_SPEC_TYPE(0x0202)

    }

    override fun toString(): String {
        return "type = ${getType(type)}, typeHexValue = ${type.getHexValue()}, headerSize = ${headerSize.getValue()}, headerHexValue = ${headerSize.getHexValue()}, size = ${size.getValue()}, sizeHexValue = ${size.getHexValue()}"
    }

    fun getType(type: uint16_t): String {
        when (type.getValue().toInt()) {
            ResChunk_header.ChunkType.RES_NULL_TYPE.type -> return "RES_NULL_TYPE"
            ResChunk_header.ChunkType.RES_STRING_POOL_TYPE.type -> return "RES_STRING_POOL_TYPE"
            ResChunk_header.ChunkType.RES_TABLE_TYPE.type -> return "RES_TABLE_TYPE"
            ResChunk_header.ChunkType.RES_XML_TYPE.type -> return "RES_XML_TYPE"
            ResChunk_header.ChunkType.RES_XML_FIRST_CHUNK_TYPE.type -> return "RES_XML_FIRST_CHUNK_TYPE"
            ResChunk_header.ChunkType.RES_XML_START_NAMESPACE_TYPE.type -> return "RES_XML_START_NAMESPACE_TYPE"
            ResChunk_header.ChunkType.RES_XML_END_NAMESPACE_TYPE.type -> return "RES_XML_END_NAMESPACE_TYPE"
            ResChunk_header.ChunkType.RES_XML_START_ELEMENT_TYPE.type -> return "RES_XML_START_ELEMENT_TYPE"
            ResChunk_header.ChunkType.RES_XML_END_ELEMENT_TYPE.type -> return "RES_XML_END_ELEMENT_TYPE"
            ResChunk_header.ChunkType.RES_XML_CDATA_TYPE.type -> return "RES_XML_CDATA_TYPE"
            ResChunk_header.ChunkType.RES_XML_LAST_CHUNK_TYPE.type -> return "RES_XML_LAST_CHUNK_TYPE"
            ResChunk_header.ChunkType.RES_XML_RESOURCE_MAP_TYPE.type -> return "RES_XML_RESOURCE_MAP_TYPE"
            ResChunk_header.ChunkType.RES_TABLE_PACKAGE_TYPE.type -> return "RES_TABLE_PACKAGE_TYPE"
            ResChunk_header.ChunkType.RES_TABLE_TYPE_TYPE.type -> return "RES_TABLE_TYPE_TYPE"
            ResChunk_header.ChunkType.RES_TABLE_TYPE_SPEC_TYPE.type -> return "RES_TABLE_TYPE_SPEC_TYPE"
        }

        return ""
    }
}
