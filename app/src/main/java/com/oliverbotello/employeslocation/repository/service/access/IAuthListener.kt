package com.oliverbotello.employeslocation.repository.service.access

interface IAuthListener {
    fun onSuccessLogin()
    fun onFailedLogin()
}