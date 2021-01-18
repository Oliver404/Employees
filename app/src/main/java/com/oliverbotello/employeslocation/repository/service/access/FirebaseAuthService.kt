package com.oliverbotello.employeslocation.repository.service.access

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthService() : AuthService(), OnCompleteListener<AuthResult> {
    companion object {
        private var auth: FirebaseAuth? = null
    }

    init {
        if (auth == null)
            auth = Firebase.auth
    }

    override fun checkUser(): Unit {
        if (FirebaseAuthService.auth?.currentUser != null)
            this.authListener?.onSuccessLogin()
        else
            this.authListener?.onFailedLogin()
    }

    override fun createUser(email: String, password: String): Unit {
        FirebaseAuthService.auth
            ?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this)
    }

    override fun login(email: String, password: String) {
        FirebaseAuthService.auth
            ?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this)
    }

    override fun logout() {
        FirebaseAuthService.auth?.signOut()
    }

    override fun onComplete(resultTask: Task<AuthResult>) {
        if (resultTask.isSuccessful) {
            this.registerListener?.onSuccessRegiter()
            this.authListener?.onSuccessLogin()
        }
        else {
            this.registerListener?.onFailedRegister()
            this.authListener?.onFailedLogin()
        }
    }
}