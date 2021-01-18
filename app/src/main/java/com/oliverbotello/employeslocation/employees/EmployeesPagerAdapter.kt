package com.oliverbotello.employeslocation.employees

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class EmployeesPagerAdapter(val listener: IEmployeesPagerAdapterListener, fm: FragmentManager)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = this.listener.getCount()

    override fun getPageTitle(position: Int): CharSequence = this.listener.getPageTitle(position)

    override fun getItem(position: Int): Fragment = this.listener.getItem(position)
}