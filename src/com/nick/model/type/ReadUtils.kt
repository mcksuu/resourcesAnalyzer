package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t
import com.nick.model.unit.uint64_t
import com.nick.model.unit.uint8_t
import java.io.InputStream
import kotlin.experimental.and
import kotlin.experimental.or

/**
 * Created by KangShuai on 2017/11/29.
 */

var readSum: Int = 0
var stringList: Array<String>? = null
fun read_uint8_t(stream: InputStream): uint8_t {
    val byte = ByteArray(1)
    stream.read(byte)
    readSum++
    return uint8_t(byte[0])
}

fun read_uint8_t(byteArray: ByteArray, offset: Int): uint8_t {
    return uint8_t(byteArray[offset])
}

fun byte22Uint16_t(byteArray: ByteArray): uint16_t {
    var value: Short = 0
    for (i in 0..1) {
        value = (value.toInt() shl 8).toShort()
        value = value or (byteArray[i].toInt() and 0xFF).toShort()
    }
    return uint16_t(value)
}

fun read_uint16_t(stream: InputStream): uint16_t {
    readSum += 2
    val byte = ByteArray(2)
    stream.read(byte)
    var value: Short = 0
    for (i in 0..1) {
        value = (value.toInt() shl 8).toShort()
        value = value or (byte[i].toInt() and 0xFF).toShort()
    }
    return uint16_t(value)
}

fun read_uint16_t(stream: ByteArray, index: Int): uint16_t {
    val byte = ByteArray(2)
    byte[0] = stream[index]
    byte[1] = stream[index + 1]

    var value: Short = 0
    for (i in 0..1) {
        value = (value.toInt() shl 8).toShort()
        value = value or (byte[i].toInt() and 0xFF).toShort()
    }
    return uint16_t(value)
}

fun read_uint32_t(stream: InputStream): uint32_t {
    readSum += 4
    val byte = ByteArray(4)
    stream.read(byte)
    var value: Int = 0
    for (i in 0..3) {
        value = value shl 8
        value = value or (byte[i].toInt() and 0xFF)
    }
    return uint32_t(value)
}

fun read_uint32_t(stream: ByteArray, index: Int): uint32_t {
    val byte = ByteArray(4)
    for (i in 0..byte.size - 1) {
        byte[i] = stream[index + i]
    }

    var value: Int = 0
    for (i in 0..3) {
        value = value shl 8
        value = value or (byte[i].toInt() and 0xFF)
    }
    return uint32_t(value)
}

fun read_uint64_t(stream: InputStream): uint64_t {
    readSum += 8
    val byte = ByteArray(8)
    stream.read(byte)
    var value: Long = 0
    for (i in 0..7) {
        value = value shl 8
        value = value or (byte[i].toLong() and 0xFF)
    }
    return uint64_t(value)
}

fun read_uint64_t(stream: ByteArray, index: Int): uint64_t {
    val byte = ByteArray(8)
    for (i in 0..byte.size - 1) {
        byte[i] = stream[index + i]
    }

    var value: Long = 0
    for (i in 0..7) {
        value = value shl 8
        value = value or (byte[i].toLong() and 0xFF)
    }
    return uint64_t(value)
}

fun readString2(stream: ByteArray, size: Int, index: Int): Pair<String, Int> {
    val bytes = ByteArray(1)
    val byteArray = ByteArray(1)
    var realSize = 0
    bytes[0] = stream[index]
    byteArray[0] = stream[index + 1]
    var c = 1
    if (bytes[0].toInt() and 0x80 != 0) {
        c++
        byteArray[0] = stream[index + c]
    }
    var result = ""
    realSize = byteArray[0].toInt()
    var i = 0
    while (realSize and (0x80 shl i) != 0) {
        c++
        byteArray[0] = stream[index + c]
        realSize = ((realSize and 0x7f) shl 8) or (byteArray[0].toInt() and 0xff)
        i += 4
    }

    if (realSize > 0) {
        val tempBytes = ByteArray(realSize)
        for (j in 0..tempBytes.size - 1) {
            tempBytes[j] = stream[index + c + 1 + j]
        }
        result += "${String(tempBytes)}  realSize = $realSize size = $size c = $c"
    }

    val resultPair = Pair(result, realSize + c + i + 1)

    return resultPair
}

fun readString2(stream: InputStream, size: Int): String {

    val bytes = ByteArray(1)
    val byteArray = ByteArray(1)
    var realSize = 0
    stream.read(bytes)
    stream.read(byteArray)
    readSum += 2
    if (bytes[0].toInt() and 0x80 != 0) {
        stream.read(byteArray)
        readSum++
    }
    var result = ""
    realSize = byteArray[0].toInt()
    var c = 2
    var i = 0
    while (realSize and (0x80 shl i) != 0) {
        c++
        stream.read(byteArray)
        readSum++
        realSize = ((realSize and 0x7f) shl 8) or (byteArray[0].toInt() and 0xff)
        i += 4
    }

    if (realSize > 0) {
        val tempBytes = ByteArray(realSize)
        stream.read(tempBytes)
        readSum += realSize
        result += "${String(tempBytes)}  realSize = $realSize size = $size c = $c"
    }

    stream.read(kotlin.ByteArray(1))
    readSum += 1
    return result
}

fun readStringWithSize(stream: InputStream, size: Int): String {
    var result = ""
    readSum += size
    if (size > 0) {
        val tempBytes = ByteArray(size)
        stream.read(tempBytes)
        result = String(tempBytes)
    }

    return result
}

fun readStringWithSize(stream: ByteArray, offset: Int, size: Int): String {
    var result = ""
    if (size > 0) {
        val tempBytes = ByteArray(size)

        for (i in 0..tempBytes.size - 1) {
            tempBytes[i] = stream[offset + i]
        }

        result = String(tempBytes)
    }

    return result
}

fun getResChunk_header(stream: InputStream, type: ResChunk_header.ChunkType): ResChunk_header {
    val byteArray = ByteArray(2)
    stream.read(byteArray)
    readSum += 2
    while (byte22Uint16_t(byteArray).getValue().toInt() != type.type) {
        val uint8_t = read_uint8_t(stream)
        byteArray[0] = byteArray[1]
        byteArray[1] = uint8_t.getValue()
    }
    return ResChunk_header(byte22Uint16_t(byteArray), read_uint16_t(stream), read_uint32_t(stream))
}

fun getResChunk_header_tow_type(stream: InputStream, type: ResChunk_header.ChunkType, type2: ResChunk_header.ChunkType): ResChunk_header {
    val byteArray = ByteArray(2)
    stream.read(byteArray)
    readSum += 2
    while (byte22Uint16_t(byteArray).getValue().toInt() != type.type || byte22Uint16_t(byteArray).getValue().toInt() != type2.type) {
        val uint8_t = read_uint8_t(stream)
        byteArray[0] = byteArray[1]
        byteArray[1] = uint8_t.getValue()
    }
    return ResChunk_header(byte22Uint16_t(byteArray), read_uint16_t(stream), read_uint32_t(stream))
}

fun getResChunk_header(stream: ByteArray, index: Int): ResChunk_header {
    return ResChunk_header(read_uint16_t(stream, index), read_uint16_t(stream, index + 2), read_uint32_t(stream, index + 4))
}


fun getIntValue(value: Int): Int {
    val bytes = ByteArray(4)
    bytes[0] = (value and 0x000000ff).toByte()
    bytes[1] = ((value ushr 8) and 0x000000ff).toByte()
    bytes[2] = ((value ushr 16) and 0x000000ff).toByte()
    bytes[3] = ((value ushr 24) and 0x000000ff).toByte()
    var result: Int = 0
    for (i in 0..3) {
        result = result shl 8
        result = result or (bytes[i].toInt() and 0xFF)
    }
    return result
}

fun getLongValue(value: Long): Long {
    val bytes = ByteArray(8)
    bytes[0] = (value and 0x000000ff).toByte()
    bytes[1] = ((value ushr 8) and 0x000000ff).toByte()
    bytes[2] = ((value ushr 16) and 0x000000ff).toByte()
    bytes[3] = ((value ushr 24) and 0x000000ff).toByte()
    bytes[0] = ((value ushr 32) and 0x000000ff).toByte()
    bytes[1] = ((value ushr 40) and 0x000000ff).toByte()
    bytes[2] = ((value ushr 48) and 0x000000ff).toByte()
    bytes[3] = ((value ushr 56) and 0x000000ff).toByte()
    var result: Long = 0
    for (i in 0..bytes.size - 1) {
        result = result shl 8
        result = result or (bytes[i].toLong() and 0xFF)
    }
    return result
}

fun getShortValue(value: Short): Short {
    val bytes = ByteArray(2)
    bytes[0] = (value and 0x00ff).toByte()
    bytes[1] = ((value.toInt() shr 8) and 0x00ff).toByte()
    var result: Short = 0
    for (i in 0..1) {
        result = (result.toInt() shl 8).toShort()
        result = result or (bytes[i].toInt() and 0xFF).toShort()
    }
    return result
}

fun getStringPoolStr(index: Int): String? {
    if (stringList == null || stringList?.get(index).isNullOrEmpty()) {
        return ""
    }
    return stringList?.get(index)
}