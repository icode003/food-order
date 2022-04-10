package com.khane.data.repository

import com.khane.data.pojo.DataWrapper
import com.khane.data.pojo.User
import io.reactivex.Single

interface UserRepository {

    fun login(phone: String): Single<DataWrapper<User>>
}