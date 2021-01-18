package com.oliverbotello.employeslocation.employees.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.oliverbotello.employeslocation.databinding.FragmentEmployeesMapBinding
import com.oliverbotello.employeslocation.employees.list.EmployeesViewModel


class EmployeesMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentEmployeesMapBinding
    private lateinit var mapView: MapView
    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentEmployeesMapBinding.inflate(layoutInflater)
        val viewModel: EmployeesViewModel = ViewModelProvider(this)
            .get(EmployeesViewModel::class.java)
        this.binding.viewModel = viewModel

        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.mapView = view.findViewById<MapView>(R.id.mapVw_Employees)

        super.onViewCreated(view, savedInstanceState)
        this.mapView.onCreate(savedInstanceState)
        this.mapView.getMapAsync(this)
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

        this.binding.viewModel?.lstEmployees?.observe(
            this.viewLifecycleOwner
        ) {
            it?.let {
                val grupCoor = LatLngBounds.Builder()
                this@EmployeesMapFragment.map?.clear()

                for (employee in it) {
                    val coor = LatLng(
                        employee.location.latitude.toDouble(),
                        employee.location.longitude.toDouble()
                    )

                    this@EmployeesMapFragment.map?.addMarker(
                        MarkerOptions().position(coor).title(employee.name)
                    )
                    grupCoor.include(coor)
                }

                if (it.size > 0)
                    this@EmployeesMapFragment.map?.moveCamera(
                        CameraUpdateFactory.newLatLngBounds(grupCoor.build(), 30)
                    )
            }
        }
    }

}