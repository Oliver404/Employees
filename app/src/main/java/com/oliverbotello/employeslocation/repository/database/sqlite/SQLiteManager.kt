package com.oliverbotello.employeslocation.repository.database.sqlite

import android.content.Context
import androidx.room.Room
import com.oliverbotello.employeslocation.entities.Employee
import com.oliverbotello.employeslocation.repository.database.DBManager

class SQLiteManager(context: Context) : DBManager() {
    companion object {
        private var dbManager: EmployeeDatabase?  = null
    }

    init {
        dbManager = Room.databaseBuilder(
            context,
            EmployeeDatabase::class.java,
            "db-employees"
        ).build()
    }

    override fun findByID(id: Long): Employee =
        SQLiteManager.dbManager?.employeeDao()?.findByID(id)?: Employee()

    override fun getEmployees(): List<Employee> =
        dbManager?.employeeDao()?.getAll()?: listOf()

    override fun insertEmployee(employee: Employee) {
        dbManager?.employeeDao()?.insert(employee)
    }

    override fun insertEmployees(lstEmployees: List<Employee>) {
        dbManager?.employeeDao()?.insertAll(lstEmployees)
    }

    override fun delete(employee: Employee) {
        dbManager?.employeeDao()?.delete(employee)
    }

    override fun deleteAll() {
        dbManager?.employeeDao()?.deleteAll()
    }
}