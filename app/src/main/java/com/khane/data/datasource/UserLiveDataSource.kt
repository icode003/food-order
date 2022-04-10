package com.khane.data.datasource

import com.khane.data.pojo.DataWrapper
import com.khane.data.pojo.User
import com.khane.data.repository.UserRepository
import com.khane.data.service.AuthenticationService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLiveDataSource @Inject constructor(private val authenticationService: AuthenticationService) : BaseDataSource(), UserRepository {

    override fun login(phone: String): Single<DataWrapper<User>> {
        return execute(authenticationService.login(phone))
    }


}
