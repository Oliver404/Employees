package com.oliverbotello.employeslocation.repository.service.data

import com.oliverbotello.employeslocation.entities.Employee

interface IDataListener {
    fun onSuccessGetData(lstEmployees: List<Employee>)
    fun onFailedGetData(error: DataService.ERROR)
}