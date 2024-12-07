package com.example.vuea

import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import java.util.Locale

@CapacitorPlugin(name = "HelloWorld")
class HelloWorldPlugin : Plugin() {

    @PluginMethod
    fun echo(call: PluginCall) {
        val value = call.getString("value")
        val ret = JSObject()

        if (value != null && isInteger(value)) {
            val number = value.toInt()
            ret.put("value", calculateSquareRoot(number))
        } else if (value != null) {
            ret.put("value", value)
        } else {
            call.reject("Invalid input")
            return
        }

        call.resolve(ret)
    }

    private fun isInteger(value: String): Boolean {
        return value.toIntOrNull() != null
    }

    private fun calculateSquareRoot(number: Int): String {
        return String.format(Locale.ITALY,
            "%.2f", kotlin.math.sqrt(number.toDouble()))
    }
}
