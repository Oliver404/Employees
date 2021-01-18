package com.oliverbotello.employeslocation.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class Employee (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @Embedded
    var location: Location = Location.generateRandomLocation(),
    @ColumnInfo(name = "mail")
    var mail: String = ""
)