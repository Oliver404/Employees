package com.oliverbotello.employeslocation.adapters

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oliverbotello.employeslocation.R
import com.oliverbotello.employeslocation.entities.Employee

//class EmployeeAdapter(private val dataSet: List<Employee>)  :
//    RecyclerView.Adapter<EmployeeVH>()  {
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EmployeeVH =
//        EmployeeVH.newInstance(viewGroup)
//
//    override fun onBindViewHolder(viewHolder: EmployeeVH, position: Int) {
//        viewHolder.itemView.findViewById<AppCompatTextView>(R.id.txtVw_EmployeeName)
//            .text = this.dataSet[position].name
//    }
//
//    override fun getItemCount() = this.dataSet.size
//}

class EmployeeAdapter(private val onClick: (Employee) -> Unit)  :
    ListAdapter<Employee, EmployeeVH>(EmployeeDiffCallback)  {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EmployeeVH =
        EmployeeVH.newInstance(viewGroup, this.onClick)

    override fun onBindViewHolder(viewHolder: EmployeeVH, position: Int) {
        viewHolder.bind(this.getItem(position))
    }
}

object EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean =
        oldItem.id == newItem.id
}