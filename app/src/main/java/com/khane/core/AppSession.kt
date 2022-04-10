package com.khane.core

import android.content.Context
import android.provider.Settings

import com.google.gson.Gson
import com.khane.data.pojo.User
import com.khane.utils.LocaleHelper


import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by hlink21 on 11/7/16.
 */
@Singleton
class AppSession @Inject
constructor(
    private val appPreferences: AppPreferences,
    private val context: Context,
    @param:Named("api-key") override var apiKey: String
) : Session {

    private val gson: Gson = Gson()

    override var user: User
        get() {
            val userJSON = appPreferences.getString(USER_JSON)
            return gson.fromJson(userJSON, User::class.java)
        }
        set(value) {
            val userJson = gson.toJson(value)
            appPreferences.putString(USER_JSON, userJson)
        }


    override var token: String
        get() = appPreferences.getString(Session.TOKEN)
        set(token) = appPreferences.putString(Session.TOKEN, token)


    override var userId: String
        get() = appPreferences.getString(Session.USER_ID)
        set(userId) = appPreferences.putString(Session.USER_ID, userId)

    override/* open below comment after Firebase integration *///token = FirebaseInstanceId.getInstance().getToken();
    val deviceId: String
        get() {
            var token = ""
            if (token.isEmpty())
                token =
                    Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

            return token
        }


    override//  return StringUtils.equalsIgnoreCase(appPreferences.getString(Common.LANGUAGE), "ar") ? LANGUAGE_ARABIC : LANGUAGE_ENGLISH;
    var language: String
        get() = appPreferences.getString(Session.ACCEPT_LANGUAGE, LocaleHelper.LANGUAGE_ENGLISH)
        set(language) = appPreferences.putString(Session.ACCEPT_LANGUAGE, language)


    override var isLogin: Boolean
        get() = appPreferences.getBoolean(Session.IS_LOGIN)
        set(value) = appPreferences.putBoolean(Session.IS_LOGIN, value)


    override fun clearSession() {
        appPreferences.clearAll()
    }


    companion object {
        const val USER_JSON = "user_json"
    }


}
