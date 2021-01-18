package com.oliverbotello.employeslocation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.oliverbotello.employeslocation.access.AccessActivity
import com.oliverbotello.employeslocation.employees.EmployeesActivity

class MainActivity : AppCompatActivity(), IMain {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        this.viewModel = MainViewModel(this)
    }

    override fun logged(isLogged: Boolean) {
        val intent: Intent =
            if (isLogged) Intent(this, EmployeesActivity::class.java)
            else Intent(this, AccessActivity::class.java)

        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        this.startActivity(intent)
    }
}