package com.oliverbotello.employeslocation.employees

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.oliverbotello.employeslocation.R
import com.oliverbotello.employeslocation.access.AccessActivity
import com.oliverbotello.employeslocation.employees.detail.EmployeeDetailActivity
import com.oliverbotello.employeslocation.employees.list.EmployeesListFragment
import com.oliverbotello.employeslocation.employees.map.EmployeesMapFragment
import com.oliverbotello.employeslocation.repository.service.access.FirebaseAuthService
import com.oliverbotello.employeslocation.utils.EMPLOYEE_ID

class EmployeesActivity
    : AppCompatActivity(), IEmployeesPagerAdapterListener, View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_employees)
        this.initView()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.logout, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            val intent = Intent(this, AccessActivity::class.java)

            FirebaseAuthService().logout()
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        val vwPager: ViewPager = this.findViewById<ViewPager>(R.id.vwPgr_Employes)
        val tabs: TabLayout = this.findViewById<TabLayout>(R.id.tabLyt_Employes)
        val addBtn: FloatingActionButton =
            this.findViewById<FloatingActionButton>(R.id.fltngActBtn_AddEmployee)

        vwPager.adapter = EmployeesPagerAdapter(this, this.supportFragmentManager)
        addBtn.setOnClickListener(this)
        tabs.setupWithViewPager(vwPager)
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, EmployeeDetailActivity::class.java)

        this.startActivity(intent)
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): String =
        if (position%2 == 0) "Lista"
        else "Mapa"

    override fun getItem(position: Int): Fragment =
        if (position%2 == 0) EmployeesListFragment()
        else EmployeesMapFragment()
}