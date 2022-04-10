package com.khane.core


import com.khane.data.pojo.User
import com.khane.utils.LocaleHelper


/**
 * Created by hlink21 on 11/7/16.
 */
public interface Session {

    var apiKey: String

    var token: String

    var userId: String

    val deviceId: String

    var user: User

    var language: String

    var isLogin: Boolean

    fun clearSession()

    companion object {
        const val API_KEY = "api-key"
        const val TOKEN = "token"
        const val USER_ID = "user_id"
        const val DEVICE_TYPE = "A"
        const val IS_LOGIN = com.khane.constance.PreferenceKey.IS_LOGIN
        const val ACCEPT_LANGUAGE = LocaleHelper.ACCEPT_LANGUAGE
    }
}
