package com.example.ecommerceadmin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceadmin.databinding.DashboardItemBinding
import com.example.ecommerceadmin.models.DashBoardItems
import com.example.ecommerceadmin.models.DashboardItemType
import com.example.ecommerceadmin.models.dashBoardItemsList

class DashBoardAdapter (val callback: (DashboardItemType) ->Unit) :RecyclerView.Adapter<DashBoardAdapter.DashBoardItemViewHolder>() {
    class DashBoardItemViewHolder(val binding: DashboardItemBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(item:DashBoardItems){
                binding.item=item

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardItemViewHolder {
        val binding=DashboardItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return DashBoardItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashBoardItemViewHolder, position: Int) {
        val item= dashBoardItemsList.get(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            callback(item.type)

        }
    }

    override fun getItemCount()= dashBoardItemsList.size
}