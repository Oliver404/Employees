package com.oliverbotello.employeslocation.access

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.oliverbotello.employeslocation.access.login.LoginFragment

class AccessPagerAdapter(val listener: IAccessPagerAdapterListener, fm: FragmentManager)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = this.listener.getCount()

    override fun getPageTitle(position: Int): CharSequence = this.listener.getPageTitle(position)

    override fun getItem(position: Int): Fragment = this.listener.getItem(position)
}