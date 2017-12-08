package com.nick

import com.nick.model.Config
import com.nick.model.Config.Companion.RESTABLE_MAP_SIZE
import com.nick.model.type.*
import com.nick.model.unit.uint32_t
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * 解析类
 * Created by KangShuai on 2017/12/6.
 */
fun main(args: Array<String>) {
    val stream = File("文件路径").inputStream()
    val os = ByteArrayOutputStream()
    val bytes = ByteArray(1024)
    var len = stream.read(bytes)
    while (len != -1) {
        os.write(bytes, 0, len)
        len = stream.read(bytes)
    }

    val offset: Int
    println("---------------------解析ResTable_header信息开始---------------------")
    val streamByte = os.toByteArray()
    val resTable_header = ResTable_header(getResChunk_header(streamByte, 0), read_uint32_t(streamByte, Config.RESCHUNK_HEADER_SIZE))
    println(resTable_header)
    println("---------------------解析ResTable_header信息完毕---------------------")
    offset = resTable_header.header.headerSize.getValue().toInt()

    val resStringPool_header = resStringPool_header(streamByte, offset)
    stringList = resStringPool_header.stringStringArray!!.stringArray
    println("读取Package信息")
    val packageOffset = resStringPool_header.header.size.getValue() + offset
    // 解析Package数据块
    val resTable_package = ResTable_package(
            getResChunk_header(streamByte, packageOffset),
            read_uint32_t(streamByte, packageOffset + Config.RESCHUNK_HEADER_SIZE),
            readStringWithSize(streamByte, packageOffset + Config.RESCHUNK_HEADER_SIZE + 4, 256),
            read_uint32_t(streamByte, packageOffset + Config.RESCHUNK_HEADER_SIZE + 4 + 256),
            read_uint32_t(streamByte, packageOffset + Config.RESCHUNK_HEADER_SIZE + 4 + 256 + 4),
            read_uint32_t(streamByte, packageOffset + Config.RESCHUNK_HEADER_SIZE + 4 + 256 + 4 + 4),
            read_uint32_t(streamByte, packageOffset + Config.RESCHUNK_HEADER_SIZE + 4 + 256 + 4 + 4 + 4))
    println(resTable_package)

    // 解析资源类型
    val resTypePoolOffset = resTable_package.header.headerSize.getValue().toInt() + packageOffset
    val resTypeStringPool_header = resStringPool_header(streamByte, resTypePoolOffset)
    // 解析资源类型值
    val resInTypeStringPoolOffset = resTypePoolOffset + resTypeStringPool_header.header.size.getValue()
    val resInTypeStringPool_header = resStringPool_header(streamByte, resInTypeStringPoolOffset)

    var resTableTypeSpecOffset = resInTypeStringPoolOffset + resInTypeStringPool_header.header.size.getValue()

    var resTypeSpecOffset = resTableTypeSpecOffset

    while (resTypeSpecOffset < streamByte.size) {

        if (read_uint16_t(streamByte, resTypeSpecOffset).getValue().toInt() == ResChunk_header.ChunkType.RES_TABLE_TYPE_SPEC_TYPE.type) {
            val resTable_typeSpec = ResTable_typeSpec(getResChunk_header(streamByte, resTypeSpecOffset),
                    read_uint8_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE),
                    read_uint8_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1),
                    read_uint16_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1),
                    read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2))
            println("$resTable_typeSpec, idValue = ${resTypeStringPool_header.stringStringArray!!.stringArray[resTable_typeSpec.id.getValue().toInt() - 1]}")

            val arrayOfUint32_ts = Array(resTable_typeSpec.entryCount.getValue(), {
                uint32_t(0)
            })

            (0..resTable_typeSpec.entryCount.getValue() - 1).forEachIndexed { index, i ->
                arrayOfUint32_ts[index] = read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 + 4 * index)
            }
            val res_entry_array = Res_entry_array(arrayOfUint32_ts)
            println(res_entry_array)
            resTypeSpecOffset += resTable_typeSpec.header.size.getValue()
            println("===========================================")
        } else {
            val resTable_type = ResTable_type(
                    getResChunk_header(streamByte, resTypeSpecOffset),
                    read_uint8_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE),
                    read_uint8_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1),
                    read_uint16_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1),
                    read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2),
                    read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4),
                    ResTable_config(
                            read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 * 2),
                            read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 * 3),
                            read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 * 4),
                            read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 * 5),
                            read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 * 6),
                            read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 * 7),
                            read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 * 8),
                            read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 * 9),
                            read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 * 10),
                            read_uint32_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 * 11),
                            read_uint64_t(streamByte, resTypeSpecOffset + Config.RESCHUNK_HEADER_SIZE + 1 + 1 + 2 + 4 * 12)
                    ))
            println(resTable_type)
            var tempOffset = resTypeSpecOffset + resTable_type.header.headerSize.getValue().toInt()

            val arrayOfUint32_ts = Array(resTable_type.entryCount.getValue(), {
                uint32_t(0)
            })

            (0..resTable_type.entryCount.getValue() - 1).forEachIndexed { index, i ->
                arrayOfUint32_ts[index] = read_uint32_t(streamByte, tempOffset + 4 * index)
            }

            val res_entry_array2 = Res_entry_array(arrayOfUint32_ts)
            println(res_entry_array2)

            tempOffset += resTable_type.entriesStart.getValue() - resTable_type.header.headerSize.getValue().toInt()

            val resString_string_array = resInTypeStringPool_header.stringStringArray
            var count = 0
            var resId = 0
            while (count < resTable_type.header.size.getValue() - resTable_type.entriesStart.getValue()) {
                println("resId = ${Integer.toHexString(resId)}")
                val size = read_uint16_t(streamByte, tempOffset + count)
                val flags = read_uint16_t(streamByte, tempOffset + count + 2)
                if (flags.getValue().toInt() == 1) {
                    val resTable_map_entry = ResTable_map_entry(size,
                            flags,
                            ResStringPool_ref(read_uint32_t(streamByte, tempOffset + count + 2 + 2)),
                            ResTable_ref(read_uint32_t(streamByte, tempOffset + count + 2 + 2 + 4)),
                            read_uint32_t(streamByte, tempOffset + count + 2 + 2 + 4 + 4))

                    val mapStr = resTable_map_entry.key!!.index.getValue()
                    resTable_map_entry.dataStr = resInTypeStringPool_header.stringStringArray!!.stringArray[mapStr]
                    println(resTable_map_entry)
                    count += resTable_map_entry.size.getValue().toInt()
                    println(resString_string_array!!.stringArray[mapStr])
                    for (index in 0..resTable_map_entry.count.getValue() - 1) {
                        val resTable_map = ResTable_map(
                                ResTable_ref(
                                        read_uint32_t(streamByte, tempOffset)),
                                Res_value(
                                        read_uint16_t(streamByte, tempOffset + 4),
                                        read_uint8_t(streamByte, tempOffset + 4 + 2),
                                        read_uint8_t(streamByte, tempOffset + 4 + 2 + 1),
                                        read_uint32_t(streamByte, tempOffset + 4 + 2 + 1 + 1)))
                        println("--------------------------resTable_map start------------------------------")
                        println(resTable_map)
                        println("--------------------------resTable_map end------------------------------")
                        count += 4 + 2 + 1 + 1 + 4
                    }
//                    tempOffset += resTable_map_entry.count.getValue() * Config.RESTABLE_MAP_SIZE
                } else {
                    val resTable_entry = ResTable_entry(size, flags, ResStringPool_ref(read_uint32_t(streamByte, tempOffset + count + 2 + 2)))
                    val index = resTable_entry.key!!.index.getValue()
                    if (index > 0 && index <= resInTypeStringPool_header.stringStringArray!!.stringArray.size - 1) {
                        resTable_entry.dataStr = resInTypeStringPool_header.stringStringArray!!.stringArray[index]
                    }
                    count += 8
                    val res_value = Res_value(read_uint16_t(streamByte, tempOffset + count),
                            read_uint8_t(streamByte, tempOffset + count + 2)
                            , read_uint8_t(streamByte, tempOffset + count + 2 + 1),
                            read_uint32_t(streamByte, tempOffset + count + 2 + 1 + 1))
                    println("--------------------------resTable_entry start------------------------------")
                    println(resTable_entry)
                    println("--------------------------resTable_entry end------------------------------")
                    println("--------------------------res_value start------------------------------")
                    println(res_value)
                    println("--------------------------res_value end------------------------------")
                    count += 8
                }

                resId++
            }
            resTypeSpecOffset += resTable_type.header.size.getValue()
            println("===========================================")
        }
    }
}

private fun resStringPool_header(streamByte: ByteArray, offset: Int): ResStringPool_header {
    println("---------------------解析ResStringPool_header信息完毕---------------------")
    val resStringPool_header = ResStringPool_header(getResChunk_header(streamByte, offset),
            read_uint32_t(streamByte, offset + Config.RESCHUNK_HEADER_SIZE),
            read_uint32_t(streamByte, offset + Config.RESCHUNK_HEADER_SIZE + 4),
            read_uint32_t(streamByte, offset + Config.RESCHUNK_HEADER_SIZE + 8),
            read_uint32_t(streamByte, offset + Config.RESCHUNK_HEADER_SIZE + 12),
            read_uint32_t(streamByte, offset + Config.RESCHUNK_HEADER_SIZE + 16),
            null,
            null,
            null,
            null)
    println(resStringPool_header)
    println("---------------------解析ResStringPool_header信息完毕---------------------")
    //解析完成后会有两个偏移数组，一个是string偏移数组，另一个是style偏移数组

    // string偏移数组
    val stringCount = resStringPool_header.stringCount.getValue()
    val resString_offset_array = ResString_offset_array(Array(stringCount, {
        uint32_t(0)
    }))
    for (i in 0..stringCount - 1) {
        resString_offset_array.offset_array[i] = read_uint32_t(streamByte, resStringPool_header.header.headerSize.getValue() + offset + i * 4)
    }

    // style偏移数组
    val styleCount = resStringPool_header.styleCount.getValue()
    val resStyle_offset_array = ResStyle_offset_array(Array(styleCount, {
        uint32_t(0)
    }))
    for (i in 0..styleCount - 1) {
        resStyle_offset_array.offset_array[i] = read_uint32_t(streamByte, resStringPool_header.header.headerSize.getValue() + stringCount * 4 + offset + i * 4)
    }

    //解析字符串池
    val stringStartOffset = offset + resStringPool_header.stringsStart.getValue()
    val stringArray: Array<String> = Array(resStringPool_header.stringCount.getValue(), {
        ""
    })

    for (i in 0..resStringPool_header.stringCount.getValue() - 1) {
        var size: Int
        if (i + 1 <= resStringPool_header.stringCount.getValue() - 1) {
            size = resString_offset_array.offset_array[i + 1].getValue() - resString_offset_array.offset_array[i].getValue()
        } else {
            size = resStringPool_header.header.size.getValue() - resString_offset_array.offset_array[i].getValue() - resStringPool_header.stringCount.getValue() * 4 - 28
        }
        val resultPair = readString2(streamByte, size, stringStartOffset + resString_offset_array.offset_array[i].getValue())
        stringArray[i] = resultPair.first
    }

    //资源字符串值
    val resString_string_array = ResString_string_array(stringArray)
    resString_string_array.stringArray.forEachIndexed { index, s ->
        println("$index : $s")
    }

    println("----------------------读取style----------------------")
    val styleStartOffset = offset + resStringPool_header.stylesStart.getValue()
    val styleStringArray: Array<ResStringPool_span> = Array(resStringPool_header.styleCount.getValue(), {
        ResStringPool_span(
                ResStringPool_ref(uint32_t(0)),
                uint32_t(0),
                uint32_t(0)
        )
    })

    for (i in 0..resStringPool_header.styleCount.getValue() - 1) {
        styleStringArray[i] = ResStringPool_span(
                ResStringPool_ref(read_uint32_t(streamByte, styleStartOffset + resStyle_offset_array.offset_array[i].getValue())),
                read_uint32_t(streamByte, styleStartOffset + resStyle_offset_array.offset_array[i].getValue() + 4),
                read_uint32_t(streamByte, styleStartOffset + resStyle_offset_array.offset_array[i].getValue() + 8)
        )
    }

    for (i in 0..resStringPool_header.styleCount.getValue() - 1) {
        println(styleStringArray[i])
    }

    resStringPool_header.stringOffsetArray = resString_offset_array
    resStringPool_header.styleOffsetArray = resStyle_offset_array
    resStringPool_header.stringStringArray = resString_string_array
    resStringPool_header.styleStringArray = ResStyle_string_array(styleStringArray)
    return resStringPool_header
}