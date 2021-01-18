package com.oliverbotello.employeslocation.access.register

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.oliverbotello.employeslocation.access.AccessViewModel
import com.oliverbotello.employeslocation.repository.service.access.IRegisterListener
import com.oliverbotello.employeslocation.utils.REGER_PASSWORD

class RegisterViewModel(application: Application)
    : AccessViewModel(application), IRegisterListener {
    val email: MutableLiveData<String> = MutableLiveData<String>("")
    val password: MutableLiveData<String> = MutableLiveData<String>("")
    val confirmPassword: MutableLiveData<String> = MutableLiveData<String>("")
    var registerListener: IRegister? = null

    init {
        this.authService.registerListener = this
    }

    fun register() {
        this.registerListener?.onStartRegister()

        val userEmail: String = this.email.value.toString()
        val userPsw: String = this.password.value.toString()
        val userPswConfirm: String = this.confirmPassword.value.toString()

        if (userEmail.isNullOrBlank())
            this.registerListener?.onFailedRegister(IRegister.ERROR.EMPTY_USER)
        else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
            this.registerListener?.onFailedRegister(IRegister.ERROR.INVALID_USER)
        else if (userPsw.isNullOrBlank())
            this.registerListener?.onFailedRegister(IRegister.ERROR.EMPTY_PASSWORD)
        else if (!userPsw.matches(Regex(REGER_PASSWORD)))
            this.registerListener?.onFailedRegister(IRegister.ERROR.INVALID_PASSWORD)
        else if (userPswConfirm.isNullOrBlank() || !userPswConfirm.equals(userPsw))
            this.registerListener?.onFailedRegister(IRegister.ERROR.NO_MATCH_PASSWORD)
        else
            this.authService.createUser(userEmail, userPsw)
    }

    override fun onSuccessRegiter() {
        this.registerListener?.onSuccessRegister()
        this.accessListener?.onStartDownloadData()
        this.dataService?.getEmployees()
    }

    override fun onFailedRegister() {
        this.registerListener?.onFailedRegister(IRegister.ERROR.INVALID_USER)
    }
}