package com.oliverbotello.employeslocation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.oliverbotello.employeslocation.R
import com.oliverbotello.employeslocation.entities.Employee
import org.jetbrains.anko.sdk27.coroutines.onClick

class EmployeeVH(view: View, val onClick: (Employee) -> Unit) : RecyclerView.ViewHolder(view) {
    private var currentEmployee: Employee? = null

    init {
        this.itemView.setOnClickListener {
            this.currentEmployee?.let {
                this.onClick(it)
            }
        }
    }

    companion object {
        fun newInstance(viewGroup: ViewGroup, onClick: (Employee) -> Unit): EmployeeVH =
            EmployeeVH(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.layout_row_employee, viewGroup, false),
                onClick
            )
    }

    fun bind(employee: Employee) {
        this.currentEmployee = employee
        this.itemView.findViewById<AppCompatTextView>(R.id.txtVw_EmployeeName).text =
            employee.name
        this.itemView.findViewById<AppCompatTextView>(R.id.txtVw_EmployeeEmail).text =
            employee.mail
    }
}