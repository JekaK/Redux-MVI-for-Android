package com.krykun.reduxmvi.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.krykun.reduxmvi.global.AppState
import com.krykun.reduxmvi.log.Log
import com.krykun.reduxmvi.log.LogLevel

class JsonStateDiffLogger(private val minLogLevel: LogLevel) : StateDiffLogger {

    private val gsonBuilder = GsonBuilder().create()
    private val stateDiffTag = "StateDiff"

    override fun printDiff(oldState: AppState, newState: AppState) {
        if (minLogLevel.priority >= LogLevel.VERBOSE.priority) {
            val oldJson = gsonBuilder.toJsonTree(oldState)
            val newJson = gsonBuilder.toJsonTree(newState)
            printDiff("", oldJson, newJson)
        }
    }

    private fun printDiff(path: String, oldElement: JsonElement, newElement: JsonElement) {
        when {
            newElement.isJsonObject -> {
                newElement.asJsonObject.entrySet().forEach { newObject ->
                    val oldObject =
                        oldElement.asJsonObject.entrySet().firstOrNull { it.key == newObject.key }
                    if (oldObject?.value != null && newObject.value != oldObject.value) {
                        printDiff("$path${newObject.key}>", oldObject.value, newObject.value)
                    }
                }
            }
            newElement.isJsonArray -> {
                Log.v(stateDiffTag, "Changed in $path")
                Log.v(stateDiffTag, "old = ${oldElement.asJsonArray}")
                Log.v(stateDiffTag, "new = ${newElement.asJsonArray}")
            }
            newElement.isJsonPrimitive -> {
                Log.v(stateDiffTag, "Changed in $path")
                Log.v(stateDiffTag, "old = ${oldElement.asJsonPrimitive}")
                Log.v(stateDiffTag, "new = ${newElement.asJsonPrimitive}")
            }
            newElement.isJsonNull -> {
                Log.v(stateDiffTag, "JSON_NULL")
            }
        }
    }
}
