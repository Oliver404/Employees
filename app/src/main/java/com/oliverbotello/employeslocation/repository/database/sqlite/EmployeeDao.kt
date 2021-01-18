package com.oliverbotello.employeslocation.repository.database.sqlite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.oliverbotello.employeslocation.entities.Employee

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees WHERE id = :id")
    fun findByID(id: Long): Employee

    @Query("SELECT * FROM employees")
    fun getAll(): List<Employee>

    @Insert
    fun insert(vararg employee: Employee)

    @Insert
    fun insertAll(employees: List<Employee>)

    @Delete
    fun delete(employee: Employee)

    @Query("DELETE FROM employees")
    fun deleteAll()
}