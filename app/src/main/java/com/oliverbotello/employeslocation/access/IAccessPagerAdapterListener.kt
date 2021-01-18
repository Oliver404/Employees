package com.oliverbotello.employeslocation.access

import androidx.fragment.app.Fragment

interface IAccessPagerAdapterListener {
    fun getCount(): Int
    fun getPageTitle(position: Int): String
    fun getItem(position: Int): Fragment
}