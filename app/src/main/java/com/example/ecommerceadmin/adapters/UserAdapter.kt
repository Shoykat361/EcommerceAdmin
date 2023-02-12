package com.example.ecommerceadmin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceadmin.databinding.UserRowBinding
import com.example.ecommerceadmin.models.UserofEcom

class UserAdapter() : ListAdapter<UserofEcom, UserAdapter.UserViewHolder>(UserDiffUtil()){
    class UserViewHolder(val binding: UserRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserofEcom) {
            binding.user = user
        }
    }

    class UserDiffUtil : DiffUtil.ItemCallback<UserofEcom>() {
        override fun areItemsTheSame(oldItem: UserofEcom, newItem: UserofEcom): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: UserofEcom, newItem: UserofEcom): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

    }
}