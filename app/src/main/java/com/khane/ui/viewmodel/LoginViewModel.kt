package com.khane.ui.viewmodel

import com.khane.data.pojo.User
import com.khane.data.repository.UserRepository
import com.khane.ui.base.APILiveData
import com.khane.ui.base.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    val loginLiveData = APILiveData<User>()

    fun login(phoneNumber: String) {

        userRepository.login(phoneNumber)
                .subscribe(withLiveData(loginLiveData))
    }
}