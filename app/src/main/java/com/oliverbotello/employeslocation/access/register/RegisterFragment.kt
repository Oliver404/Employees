package com.oliverbotello.employeslocation.access.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.oliverbotello.employeslocation.access.IAccess
import com.oliverbotello.employeslocation.databinding.FragmentRegisterBinding
import com.oliverbotello.employeslocation.employees.EmployeesActivity

class RegisterFragment : Fragment(), IRegister, IAccess {
    private lateinit var binding: FragmentRegisterBinding
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentRegisterBinding.inflate(layoutInflater)
        val viewModel: RegisterViewModel = ViewModelProvider(this)
            .get(RegisterViewModel::class.java)
        viewModel.registerListener = this
        viewModel.accessListener = this
        this.binding.viewModel = viewModel

        return this.binding.root
    }

    override fun onStartRegister() {
        this.showDialog()
    }

    override fun onSuccessRegister() {
        this.dismissDialog()
    }

    override fun onFailedRegister(error: IRegister.ERROR) {
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

    private fun showError(error: IRegister.ERROR) {
        when(error) {
            IRegister.ERROR.EMPTY_USER,
            IRegister.ERROR.INVALID_USER ->
                this.binding.txtInptLytEmail.error = this.getString(error.idMessage)
            IRegister.ERROR.EMPTY_PASSWORD,
            IRegister.ERROR.INVALID_PASSWORD ->
                this.binding.txtInptLytPassword.error = this.getString(error.idMessage)
            IRegister.ERROR.NO_MATCH_PASSWORD ->
                this.binding.txtInptLytConfirmPassword.error = this.getString(error.idMessage)
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