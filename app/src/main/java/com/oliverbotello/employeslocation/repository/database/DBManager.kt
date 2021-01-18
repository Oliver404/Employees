package com.oliverbotello.employeslocation.repository.database

import com.oliverbotello.employeslocation.entities.Employee

abstract class DBManager {
    abstract fun findByID(id: Long): Employee

    abstract fun getEmployees(): List<Employee>

    abstract fun insertEmployee(employee: Employee): Unit

    abstract fun insertEmployees(lstEmployees: List<Employee>): Unit

    abstract fun delete(employee: Employee)

    abstract fun deleteAll()
}