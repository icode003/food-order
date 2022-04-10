package com.khane.data


import okhttp3.HttpUrl

/**
 * Created by hlink21 on 11/5/17.
 */

object URLFactory {

    // server details
    private const val IS_LOCAL = false
    private const val SCHEME = "https"
    private val HOST = if (IS_LOCAL) "192.168.1.103" else "skkyn.com"
    private val API_PATH = if (IS_LOCAL) "api/v2/" else "websitedata/api/v2/"

    fun provideHttpUrl(): HttpUrl {
        return HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .addPathSegments(API_PATH)
                .build()
    }


    // API Methods
    object Method {
        const val LOGIN = "user/login"
    }

}
