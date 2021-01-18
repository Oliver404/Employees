package com.oliverbotello.employeslocation

import androidx.lifecycle.ViewModel
import com.oliverbotello.employeslocation.repository.service.access.FirebaseAuthService
import com.oliverbotello.employeslocation.repository.service.access.IAuthListener

class MainViewModel(val listener: IMain) : ViewModel(), IAuthListener {
    private var auth: FirebaseAuthService

    init {
        this.auth = FirebaseAuthService()
        this.auth.authListener = this

        this.auth.checkUser()
    }

    override fun onSuccessLogin() {
        this.listener.logged(true)
    }

    override fun onFailedLogin() {
        this.listener.logged(false)
    }
}