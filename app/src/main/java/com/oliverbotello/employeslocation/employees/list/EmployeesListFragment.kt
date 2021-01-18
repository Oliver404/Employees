package com.oliverbotello.employeslocation.employees.list

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.oliverbotello.employeslocation.R
import com.oliverbotello.employeslocation.adapters.EmployeeAdapter
import com.oliverbotello.employeslocation.databinding.FragmentEmployeesListBinding
import com.oliverbotello.employeslocation.employees.detail.EmployeeDetailActivity
import com.oliverbotello.employeslocation.entities.Employee
import com.oliverbotello.employeslocation.utils.EMPLOYEE_ID

class EmployeesListFragment : Fragment() {
    private lateinit var binding: FragmentEmployeesListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentEmployeesListBinding.inflate(layoutInflater)
        val recyclerView: RecyclerView = this@EmployeesListFragment.binding.root
            .findViewById(R.id.rcclrVw_Employees)
        val employeesAdapter: EmployeeAdapter =
            EmployeeAdapter { employee -> this.onClickItem(employee) }
        val viewModel: EmployeesViewModel = ViewModelProvider(this)
            .get(EmployeesViewModel::class.java)
        recyclerView.adapter = employeesAdapter
        this.binding.viewModel = viewModel

        viewModel.lstEmployees.observe(
            this.viewLifecycleOwner,
            {
                it?.let {
                    employeesAdapter.submitList(it)
                }
            }
        )

        return this.binding.root
    }

    private fun onClickItem(employee: Employee) {
        val intent = Intent(this.context, EmployeeDetailActivity::class.java)

        intent.putExtra(EMPLOYEE_ID, employee.id.toLong())
        this.startActivity(intent)
    }
}