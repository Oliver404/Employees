package com.oliverbotello.employeslocation.employees.list

import com.oliverbotello.employeslocation.R
import com.oliverbotello.employeslocation.entities.Employee

interface IEmployees {
    enum class ERROR(val idMessage: Int) {
        EMPTY_USER(R.string.error_empty_user),
        INVALID_USER(R.string.error_unregistered_user),
        EMPTY_PASSWORD(R.string.error_empty_password)
    }

    fun onSucces(lstEmployees: List<Employee>)
}