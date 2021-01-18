package com.oliverbotello.employeslocation.employees.list

import android.app.Application
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oliverbotello.employeslocation.entities.Employee
import com.oliverbotello.employeslocation.repository.database.DBManager
import com.oliverbotello.employeslocation.repository.database.sqlite.SQLiteManager
import com.oliverbotello.employeslocation.repository.service.data.DataFileService
import com.oliverbotello.employeslocation.repository.service.data.DataService
import com.oliverbotello.employeslocation.repository.service.data.IDataListener
import org.jetbrains.anko.doAsync

class EmployeesViewModel(application: Application) : AndroidViewModel(application), IDataListener {
    val lstEmployees: MutableLiveData<MutableList<Employee>> = MutableLiveData(mutableListOf())

    init {
        val dbManager: DBManager =
            SQLiteManager((this.getApplication() as Application).applicationContext)

        doAsync {
            this@EmployeesViewModel.lstEmployees.value?.addAll(dbManager.getEmployees())
        }
    }

    override fun onSuccessGetData(lstEmployees: List<Employee>) {
        val dbManager: DBManager =
            SQLiteManager((this.getApplication() as Application).applicationContext)

        doAsync {
            dbManager.insertEmployees(lstEmployees)

            this@EmployeesViewModel.lstEmployees.value?.addAll(dbManager.getEmployees())
        }
    }

    override fun onFailedGetData(error: DataService.ERROR) {
    }
}