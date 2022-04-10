package com.khane.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

object JsonUtils {

    fun getObjectListFromString(
        jsonString: String, listType: Class<*>
    ): List<Any> {
        if (jsonString.isNotEmpty()) {
            val typeList =
                Gson().fromJson<List<Any>>(
                    jsonString,
                    TypeToken.getParameterized(ArrayList::class.java, listType).type
                )
            typeList?.let { return it }
        }
        return ArrayList<Any>()
    }

    fun getStringFromJsonObject(jsonObject: JsonObject?): String {
        jsonObject?.let {
            val jsonObjType = object : TypeToken<JsonObject?>() {}.type
            return Gson().toJson(it, jsonObjType)
        }
        return ""
    }


}