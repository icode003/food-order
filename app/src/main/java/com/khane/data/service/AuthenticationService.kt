package com.khane.data.service

import com.khane.data.URLFactory
import com.khane.data.pojo.ResponseBody
import com.khane.data.pojo.User
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationService {

    @FormUrlEncoded
    @POST(URLFactory.Method.LOGIN)
    fun login(@Field("phone_no") phone: String): Single<ResponseBody<User>>

}