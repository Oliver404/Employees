package com.oliverbotello.employeslocation.access

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.oliverbotello.employeslocation.entities.Employee
import com.oliverbotello.employeslocation.repository.database.DBManager
import com.oliverbotello.employeslocation.repository.database.sqlite.SQLiteManager
import com.oliverbotello.employeslocation.repository.service.access.AuthService
import com.oliverbotello.employeslocation.repository.service.access.FirebaseAuthService
import com.oliverbotello.employeslocation.repository.service.data.DataFileService
import com.oliverbotello.employeslocation.repository.service.data.DataService
import com.oliverbotello.employeslocation.repository.service.data.IDataListener
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

abstract class AccessViewModel(application: Application)
    : AndroidViewModel(application), IDataListener{
    val authService: AuthService = FirebaseAuthService()
    val dataService: DataFileService
    var accessListener: IAccess? = null

    init {
        this.dataService = DataFileService()
        this.dataService.dataListener = this
    }

    override fun onSuccessGetData(lstEmployees: List<Employee>) {
        val dbManager: DBManager =
            SQLiteManager((this.getApplication() as Application).applicationContext)

        doAsync {
            dbManager.deleteAll()
            dbManager.insertEmployees(lstEmployees)
            uiThread {
                this@AccessViewModel.accessListener?.onSuccessDownloadData()
            }
        }
    }

    override fun onFailedGetData(error: DataService.ERROR) {
        this.accessListener?.onFailedDownloadData()
    }
}