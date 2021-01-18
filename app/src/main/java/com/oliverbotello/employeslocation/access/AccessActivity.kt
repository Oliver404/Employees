package com.oliverbotello.employeslocation.access

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.oliverbotello.employeslocation.R
import com.oliverbotello.employeslocation.access.login.LoginFragment
import com.oliverbotello.employeslocation.access.register.RegisterFragment
import java.util.jar.Manifest

class AccessActivity : AppCompatActivity(), IAccessPagerAdapterListener {
    private val requestCode = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_access)
        this.initView()

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        this.requestPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.size < 1 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
            val builderDialog: AlertDialog = AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.alert_necesary_permission)
                .setPositiveButton(
                    R.string.accept_button,
                    {
                        dialogInterface: DialogInterface, i: Int ->
                            dialogInterface.dismiss()
                            this.requestPermission()
                    }
                )
                .show()
        }
    }

    private fun initView() {
        val vwPager: ViewPager = this.findViewById<ViewPager>(R.id.vwPgr_Access)
        val tabs: TabLayout = this.findViewById<TabLayout>(R.id.tabLyt_Access)

        vwPager.adapter = AccessPagerAdapter(this, this.supportFragmentManager)
        tabs.setupWithViewPager(vwPager)
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): String =
        if (position%2 == 0) "Login"
        else "Registro"

    override fun getItem(position: Int): Fragment =
        if (position%2 == 0) LoginFragment()
        else RegisterFragment()

    private fun requestPermission() {
        val isActive: Boolean = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        if (!isActive)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                requestCode
            )
    }
}