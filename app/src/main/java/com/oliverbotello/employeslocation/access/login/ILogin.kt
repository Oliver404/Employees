package com.oliverbotello.employeslocation.access.login

import com.oliverbotello.employeslocation.R

interface ILogin {
    enum class ERROR(val idMessage: Int) {
        EMPTY_USER(R.string.error_empty_user),
        INVALID_USER(R.string.error_unregistered_user),
        EMPTY_PASSWORD(R.string.error_empty_password)
    }

    fun onStartLogin()
    fun onSuccessLogin()
    fun onFailedLogin(error: ERROR)
}