package com.oliverbotello.employeslocation.access.login

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.oliverbotello.employeslocation.access.AccessViewModel
import com.oliverbotello.employeslocation.repository.service.access.IAuthListener

class LoginViewModel(application: Application)
    : AccessViewModel(application), IAuthListener {
    val email: MutableLiveData<String> = MutableLiveData<String>("")
    val password: MutableLiveData<String> = MutableLiveData<String>("")
    var authListener: ILogin? = null

    init {
        this.authService.authListener = this
    }

    fun login() {
        this.authListener?.onStartLogin()

        val userEmail: String = this.email.value.toString()
        val userPsw: String = this.password.value.toString()

        if (userEmail.isNullOrBlank())
            this.authListener?.onFailedLogin(ILogin.ERROR.EMPTY_USER)
        else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
            this.authListener?.onFailedLogin(ILogin.ERROR.INVALID_USER)
        else if (userPsw.isNullOrBlank())
            this.authListener?.onFailedLogin(ILogin.ERROR.EMPTY_PASSWORD)
        else {
            this.authService.login(userEmail, userPsw)
        }
    }

    override fun onSuccessLogin() {
        this.authListener?.onSuccessLogin()
        this.accessListener?.onStartDownloadData()
        this.dataService?.getEmployees()
    }

    override fun onFailedLogin() {
        this.authListener?.onFailedLogin(ILogin.ERROR.INVALID_USER)
    }
}