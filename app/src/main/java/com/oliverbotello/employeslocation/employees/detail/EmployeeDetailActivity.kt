package com.oliverbotello.employeslocation.employees.detail

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.oliverbotello.employeslocation.R
import com.oliverbotello.employeslocation.access.register.IRegister
import com.oliverbotello.employeslocation.databinding.ActivityEmployeeDetailBinding
import com.oliverbotello.employeslocation.databinding.FragmentLoginBinding
import com.oliverbotello.employeslocation.employees.EmployeesActivity
import com.oliverbotello.employeslocation.utils.EMPLOYEE_ID

class EmployeeDetailActivity : AppCompatActivity(), OnMapReadyCallback, IDetail {
    private lateinit var binding: ActivityEmployeeDetailBinding
    private lateinit var mapView: MapView
    private var map: GoogleMap? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityEmployeeDetailBinding.inflate(layoutInflater)
        val viewModel = ViewModelProvider(this)
            .get(EmployeeDetailViewModel::class.java)
        viewModel.saveListener = this
        this.binding.lifecycleOwner = this
        this.binding.viewModel = viewModel
        this.mapView = this.binding.root.findViewById<MapView>(R.id.mpVw_EmployeeLocation)

        this.binding.viewModel?.location?.observe(
            this
        ) {
            this@EmployeeDetailActivity.map?.clear()

            val coor = LatLng(it.latitude.toDouble(), it.longitude.toDouble())

            this@EmployeeDetailActivity.map?.addMarker(MarkerOptions().position(coor))
            this@EmployeeDetailActivity.map?.moveCamera(CameraUpdateFactory.newLatLng(coor))
        }
        this.setContentView(binding.root)
        this.mapView.onCreate(savedInstanceState)
        this.mapView.getMapAsync(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        val employeeID: Long = this.intent.extras?.getLong(EMPLOYEE_ID, 0L)?:0L

        super.onPostCreate(savedInstanceState)
        this.binding.viewModel?.findEmployee(employeeID)
    }

    override fun onResume() {
        super.onResume()
        this.mapView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        this.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        this.mapView.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
    }

    override fun onStartSave() {
        this.showDialog()
    }

    override fun onSuccessSave() {
        val intent = Intent(this, EmployeesActivity::class.java)

        this.dismissDialog()
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        this.startActivity(intent)
    }

    override fun onFailedSave(error: IDetail.ERROR) {
        this.dismissDialog()
        this.showError(error)
    }

    private fun showError(error: IDetail.ERROR) {
        when(error) {
            IDetail.ERROR.EMPTY_NAME ->
                this.binding.txtInptLytEmployeeName.error = this.getString(error.idMessage)
            IDetail.ERROR.EMPTY_EMAIL,
            IDetail.ERROR.INVALID_EMAIL ->
                this.binding.txtInptLytEmployeeEmail.error = this.getString(error.idMessage)
            IDetail.ERROR.ERROR_SAVE ->
                Toast.makeText(this, this.getString(error.idMessage), Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun showDialog() {
        this.progressDialog = ProgressDialog(this)
        this.progressDialog?.isIndeterminate = true

        this.progressDialog?.setMessage("Procesando...")
        this.progressDialog?.setCancelable(false)
        this.progressDialog?.show()
    }

    private fun dismissDialog() {
        if (this.progressDialog?.isShowing == true)
            this.progressDialog?.dismiss()
    }
}