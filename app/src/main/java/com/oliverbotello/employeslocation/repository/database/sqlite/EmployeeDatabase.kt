package com.oliverbotello.employeslocation.repository.database.sqlite

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oliverbotello.employeslocation.entities.Employee

@Database(entities = arrayOf(Employee::class), version = 1)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}