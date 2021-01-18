package com.oliverbotello.employeslocation.employees.detail

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.oliverbotello.employeslocation.access.register.IRegister
import com.oliverbotello.employeslocation.entities.Employee
import com.oliverbotello.employeslocation.entities.Location
import com.oliverbotello.employeslocation.repository.database.DBManager
import com.oliverbotello.employeslocation.repository.database.sqlite.SQLiteManager
import com.oliverbotello.employeslocation.utils.REGER_PASSWORD
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EmployeeDetailViewModel(application: Application) : AndroidViewModel(application) {
    private var employee: Employee = Employee()
    val name: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val isEditable: MutableLiveData<Boolean> = MutableLiveData(true)
    val location: MutableLiveData<Location>
    val dbManager: DBManager
    var saveListener: IDetail? = null

    init {
        this.dbManager = SQLiteManager(application.applicationContext)
        this.location = MutableLiveData()
    }

    fun saveEmployee() {
        this.saveListener?.onStartSave()
        val employeeName: String = this.name.value.toString()
        val employeeEmail: String = this.email.value.toString()

        if (employeeName.isNullOrBlank())
            this.saveListener?.onFailedSave(IDetail.ERROR.EMPTY_NAME)
        else if (employeeEmail.isNullOrBlank())
            this.saveListener?.onFailedSave(IDetail.ERROR.EMPTY_EMAIL)
        else if (!Patterns.EMAIL_ADDRESS.matcher(employeeEmail).matches())
            this.saveListener?.onFailedSave(IDetail.ERROR.INVALID_EMAIL)
        else
            this.saveData()
    }

    fun findEmployee(id: Long) {
        if (id != 0L) {
            doAsync {
                val employee = dbManager.findByID(id)

                if (employee.id != 0) {
                    uiThread {
                        it.setEmployee(employee)
                    }
                }
            }
        }
        else
            this.location.value = this.employee.location
    }

    private fun setEmployee(employee: Employee) {
        this.employee = employee
        this.isEditable.value = false
        this.name.value = employee.name
        this.email.value = employee.mail
        this.location.value = employee.location
    }

    private fun saveData() {
        doAsync {
            this@EmployeeDetailViewModel.employee.name =
                this@EmployeeDetailViewModel.name.value?:""
            this@EmployeeDetailViewModel.employee.mail =
                this@EmployeeDetailViewModel.email.value?:""
            this@EmployeeDetailViewModel.employee.location =
                this@EmployeeDetailViewModel.location.value?: Location()

            try{
                this@EmployeeDetailViewModel.dbManager
                    .insertEmployee(this@EmployeeDetailViewModel.employee)
                this@EmployeeDetailViewModel.saveListener?.onSuccessSave()
            }
            catch (e: Exception) {
                this@EmployeeDetailViewModel.saveListener?.onFailedSave(IDetail.ERROR.ERROR_SAVE)
            }
        }
    }
}