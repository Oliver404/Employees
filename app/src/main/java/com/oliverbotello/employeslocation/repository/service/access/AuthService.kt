package com.oliverbotello.employeslocation.repository.service.access

abstract class AuthService {
    var authListener: IAuthListener? = null
    var registerListener: IRegisterListener? = null

    abstract fun checkUser(): Unit
    abstract fun createUser(email: String, password: String): Unit
    abstract fun login(email: String, password: String): Unit
    abstract fun logout(): Unit
}