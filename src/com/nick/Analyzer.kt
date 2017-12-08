package com.nick

import com.nick.model.type.*
import com.nick.model.unit.uint32_t
import java.io.File
import java.io.FileInputStream

/**
 * Created by KangShuai on 2017/11/29.
 */
fun main(args: Array<String>) {
    val stream = File("H:\\javaworkpace\\idea\\resourcesAnalyzer\\src\\com\\nick\\resources.arsc").inputStream()
    //解析资源索引表的头部信息
    val resTable_header = ResTable_header(getResChunk_header(stream, ResChunk_header.ChunkType.RES_TABLE_TYPE), read_uint32_t(stream))
    if (readSum != resTable_header.header.headerSize.getValue().toInt()) {
        stream.read(kotlin.ByteArray(resTable_header.header.headerSize.getValue().toInt() - readSum))
        readSum = resTable_header.header.headerSize.getValue().toInt()
    }
    println(resTable_header)

    val readStringPool = readStringPool(stream)

    //解析Package数据块
    val resTable_package = ResTable_package(
            getResChunk_header(stream, ResChunk_header.ChunkType.RES_TABLE_PACKAGE_TYPE),
            read_uint32_t(stream),
            readStringWithSize(stream, 256),
            read_uint32_t(stream),
            read_uint32_t(stream),
            read_uint32_t(stream),
            read_uint32_t(stream))
    println(resTable_package)

    println("解析资源类型字符串池")
    val resStrTypePool = readStringPool(stream)
    correctPos(stream)

    println("解析资源字符串池")
    val resStrPool = readStringPool(stream)

    println("解析ResTypeSpec和ResTypeInfo")

    val resTable_typeSpec = ResTable_typeSpec(getResChunk_header(stream, ResChunk_header.ChunkType.RES_TABLE_TYPE_SPEC_TYPE),
            read_uint8_t(stream),
            read_uint8_t(stream),
            read_uint16_t(stream),
            read_uint32_t(stream))
    println("$resTable_typeSpec, idValue = ${(resStrTypePool.get(1) as ResString_string_array).stringArray[resTable_typeSpec.id.getValue().toInt() - 1]}")

    val res_entry_array = Res_entry_array(Array(resTable_typeSpec.entryCount.getValue(), {
        read_uint32_t(stream)
    }))

    println(res_entry_array)

    val resTable_type = ResTable_type(getResChunk_header(stream, ResChunk_header.ChunkType.RES_TABLE_TYPE_TYPE),
            read_uint8_t(stream),
            read_uint8_t(stream),
            read_uint16_t(stream),
            read_uint32_t(stream),
            read_uint32_t(stream),
            ResTable_config(read_uint32_t(stream),
                    read_uint32_t(stream),
                    read_uint32_t(stream),
                    read_uint32_t(stream),
                    read_uint32_t(stream),
                    read_uint32_t(stream),
                    read_uint32_t(stream),
                    read_uint32_t(stream),
                    read_uint32_t(stream),
                    read_uint32_t(stream),
                    read_uint64_t(stream)
            ))
    read_uint32_t(stream)
    read_uint32_t(stream)
    println(resTable_type)

    val res_entry_array2 = Res_entry_array(Array(resTable_type.entryCount.getValue(), {
        read_uint32_t(stream)
    }))

    println(res_entry_array2)


    val resString_string_array = resStrPool.get(1) as ResString_string_array
    (0..resTable_type.entryCount.getValue() - 1).forEachIndexed { index, value ->
        System.out.println("resId = ${Integer.toHexString(index)}")
        val size = read_uint16_t(stream)
        val flags = read_uint16_t(stream)
        if (flags.getValue().toInt() == 1) {
            val resTable_map_entry = ResTable_map_entry(size,
                    flags,
                    ResStringPool_ref(read_uint32_t(stream)),
                    ResTable_ref(read_uint32_t(stream)),
                    read_uint32_t(stream))
            println(resTable_map_entry)
            println(resString_string_array.stringArray[resTable_map_entry.key!!.index.getValue()])
            (0..resTable_map_entry.count.getValue() - 1).forEachIndexed { index, i ->
                val resTable_map = ResTable_map(ResTable_ref(read_uint32_t(stream)), Res_value(read_uint16_t(stream), read_uint8_t(stream), read_uint8_t(stream), read_uint32_t(stream)))
                println(resTable_map)
            }

        } else {
            val res_value = Res_value(read_uint16_t(stream), read_uint8_t(stream)
                    , read_uint8_t(stream), read_uint32_t(stream))
            println(res_value)
        }

        println("===========================================")
    }
}

private fun correctPos(stream: FileInputStream) {
    if ((readSum) % 4 != 0) {
        stream.read(ByteArray(4 - (readSum) % 4))
        readSum += 4 - (readSum) % 4
    }
}

private fun readStringPool(stream: FileInputStream): HashMap<Int, Any> {
    val result = HashMap<Int, Any>()
    val resStringPool_header = ResStringPool_header(getResChunk_header(stream, ResChunk_header.ChunkType.RES_STRING_POOL_TYPE),
            read_uint32_t(stream),
            read_uint32_t(stream),
            read_uint32_t(stream),
            read_uint32_t(stream),
            read_uint32_t(stream),
            null,
            null,
            null,
            null)
    println(resStringPool_header)
    result.put(0, resStringPool_header)
    //解析资源项的值字符串资源池
    val resString_offset_array = ResString_offset_array(Array(resStringPool_header.stringCount.getValue(), {
        uint32_t(0)
    }))

    val resStyle_offset_array = ResStyle_offset_array(Array(resStringPool_header.styleCount.getValue(), {
        uint32_t(0)
    }))

    for (i in 0..resString_offset_array.offset_array.size - 1) {
        resString_offset_array.offset_array[i] = read_uint32_t(stream)
    }

    for (i in 0..resStyle_offset_array.offset_array.size - 1) {
        resStyle_offset_array.offset_array[i] = read_uint32_t(stream)
    }

    val stringArray: Array<String> = Array(resStringPool_header.stringCount.getValue(), {
        ""
    })

    for (i in 0..resStringPool_header.stringCount.getValue() - 1) {
        var size = 0
        if (i + 1 <= resStringPool_header.stringCount.getValue() - 1) {
            size = resString_offset_array.offset_array[i + 1].getValue() - resString_offset_array.offset_array[i].getValue()
        } else {
            size = resStringPool_header.header.size.getValue() - resString_offset_array.offset_array[i].getValue() - resStringPool_header.stringCount.getValue() * 4 - 28
        }
        stringArray[i] = readString2(stream, size)
    }
    //资源字符串值
    val resString_string_array = ResString_string_array(stringArray)
    result.put(1, resString_string_array)

    resString_string_array.stringArray.forEachIndexed { index, s ->
        println("$index : $s")
    }

    correctPos(stream)
    if (resStringPool_header.styleCount.getValue() > 0) {
        println("读取style")
        val styleStringArray: Array<ResStringPool_span> = Array(resStringPool_header.styleCount.getValue(), {
            ResStringPool_span(
                    ResStringPool_ref(uint32_t(0)),
                    uint32_t(0),
                    uint32_t(0)
            )
        })

        for (i in 0..resStringPool_header.styleCount.getValue() - 1) {
            styleStringArray[i] = ResStringPool_span(
                    ResStringPool_ref(read_uint32_t(stream)),
                    read_uint32_t(stream),
                    read_uint32_t(stream)
            )
            stream.read(ByteArray(4))
        }

        for (i in 0..resStringPool_header.styleCount.getValue() - 1) {
            println(styleStringArray[i])
        }

        stream.read(ByteArray(8))
    }

    return result
}
