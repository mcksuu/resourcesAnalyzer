package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t
import com.nick.model.unit.uint64_t
import com.nick.model.unit.uint8_t

class ResTable_config(
        var size: uint32_t,
        var imsi: uint32_t,
        var locale: uint32_t,
        var screenType: uint32_t,
        var input: uint32_t,
        var screenSize: uint32_t,
        var version: uint32_t,
        var screenConfig: uint32_t,
        var screenSizeDp: uint32_t,
        var localeScript: uint32_t, //4
        var localeVariant: uint64_t //4
) {

    override fun toString(): String {
        return """size = ${size.getHexValue()},
                |imsi = ${imsi.getHexValue()},
                |locale = ${locale.getHexValue()},
                |screenType = ${screenType.getHexValue()},
                |input = ${input.getHexValue()},
                |screenSize = ${screenSize.getHexValue()},
                |version = ${version.getHexValue()},
                |screenConfig = ${screenConfig.getHexValue()},
                |screenSizeDp = ${screenSizeDp.getHexValue()},
                |localeScript = ${localeScript.getHexValue()},
                |localeVariant = ${localeVariant.getHexValue()},""".trimMargin()
    }

    companion object {

        //uiMode
        val MASK_UI_MODE_TYPE = 0
        val UI_MODE_TYPE_ANY = 0x00
        val UI_MODE_TYPE_NORMAL = 0x01
        val UI_MODE_TYPE_DESK = 0x02
        val UI_MODE_TYPE_CAR = 0x03
        val UI_MODE_TYPE_TELEVISION = 0x04
        val UI_MODE_TYPE_APPLIANCE = 0x05
        val UI_MODE_TYPE_WATCH = 0x06
        val MASK_UI_MODE_NIGHT = 0
        val SHIFT_UI_MODE_NIGHT = 0
        val UI_MODE_NIGHT_ANY = 0x00
        val UI_MODE_NIGHT_NO = 0x01
        val UI_MODE_NIGHT_YES = 0x02

        //screenLayout
        val MASK_SCREENSIZE = 0
        val SCREENSIZE_ANY = 0x00
        val SCREENSIZE_SMALL = 0x01
        val SCREENSIZE_NORMAL = 0x02
        val SCREENSIZE_LARGE = 0x03
        val SCREENSIZE_XLARGE = 0x04
        val MASK_SCREENLONG = 0
        val SHIFT_SCREENLONG = 0
        val SCREENLONG_ANY = 0x00
        val SCREENLONG_NO = 0x01
        val SCREENLONG_YES = 0x02
        val MASK_LAYOUTDIR = 0
        val SHIFT_LAYOUTDIR = 0
        val LAYOUTDIR_ANY = 0x00
        val LAYOUTDIR_LTR = 0x01
        val LAYOUTDIR_RTL = 0x02
    }


}
