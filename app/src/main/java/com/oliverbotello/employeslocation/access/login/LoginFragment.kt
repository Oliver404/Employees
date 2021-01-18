package com.oliverbotello.employeslocation.access.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.oliverbotello.employeslocation.access.IAccess
import com.oliverbotello.employeslocation.access.register.IRegister
import com.oliverbotello.employeslocation.databinding.FragmentLoginBinding
import com.oliverbotello.employeslocation.employees.EmployeesActivity

class LoginFragment : Fragment(), ILogin, IAccess {
    private lateinit var binding: FragmentLoginBinding
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentLoginBinding.inflate(layoutInflater)
        val viewModel: LoginViewModel = ViewModelProvider(this)
            .get(LoginViewModel::class.java)
        viewModel.authListener = this
        viewModel.accessListener = this
        this.binding.viewModel = viewModel

        return this.binding.root
    }

    override fun onStartLogin() {
        this.showDialog()
    }

    override fun onSuccessLogin() {
        this.dismissDialog()
    }

    override fun onFailedLogin(error: ILogin.ERROR) {
        this.dismissDialog()
        this.showError(error)
    }

    override fun onStartDownloadData() {
        this.showDialog()
    }

    override fun onSuccessDownloadData() {
        val intent = Intent(this.activity, EmployeesActivity::class.java)

        this.dismissDialog()
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        this.activity?.startActivity(intent)
    }

    override fun onFailedDownloadData() {
        this.dismissDialog()
    }

    private fun showError(error: ILogin.ERROR) {
        when(error) {
            ILogin.ERROR.EMPTY_USER,
            ILogin.ERROR.INVALID_USER ->
                this.binding.txtInptLytEmail.error = this.getString(error.idMessage)
            ILogin.ERROR.EMPTY_PASSWORD ->
                this.binding.txtInptLytPassword.error = this.getString(error.idMessage)
        }
    }

    private fun showDialog() {
        this.progressDialog = ProgressDialog(this.context)
        this.progressDialog?.isIndeterminate = true

        this.progressDialog?.setMessage("Procesando...")
        this.progressDialog?.setCancelable(false)
        this.progressDialog?.show()
    }

    private fun dismissDialog() {
        if (this.progressDialog?.isShowing == true)
            this.progressDialog?.dismiss()
    }
}