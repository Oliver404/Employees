package com.oliverbotello.employeslocation.access.register

import com.oliverbotello.employeslocation.R
import com.oliverbotello.employeslocation.access.IAccess

interface IRegister {
    enum class ERROR(val idMessage: Int) {
        EMPTY_USER(R.string.error_empty_user),
        INVALID_USER(R.string.error_invalid_email),
        EMPTY_PASSWORD(R.string.error_empty_password),
        INVALID_PASSWORD(R.string.error_invalid_password),
        NO_MATCH_PASSWORD(R.string.error_match_password)
    }

    fun onStartRegister()
    fun onSuccessRegister()
    fun onFailedRegister(error: ERROR)
}