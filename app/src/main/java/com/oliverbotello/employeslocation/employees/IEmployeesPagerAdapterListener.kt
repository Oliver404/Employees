package com.oliverbotello.employeslocation.employees

import androidx.fragment.app.Fragment

interface IEmployeesPagerAdapterListener {
    fun getCount(): Int
    fun getPageTitle(position: Int): String
    fun getItem(position: Int): Fragment
}