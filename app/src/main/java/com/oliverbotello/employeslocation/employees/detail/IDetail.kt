package com.oliverbotello.employeslocation.employees.detail

import com.oliverbotello.employeslocation.R

interface IDetail {
    enum class ERROR(val idMessage: Int) {
        EMPTY_NAME(R.string.error_empty_name),
        EMPTY_EMAIL(R.string.error_empty_user),
        INVALID_EMAIL(R.string.error_invalid_email),
        ERROR_SAVE(R.string.error_save_employee)
    }

    fun onStartSave(): Unit
    fun onSuccessSave(): Unit
    fun onFailedSave(error: ERROR): Unit
}