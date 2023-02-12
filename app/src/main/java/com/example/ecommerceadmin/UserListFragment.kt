package com.example.ecommerceadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceadmin.adapters.UserAdapter
import com.example.ecommerceadmin.adapters.UserViewModel
import com.example.ecommerceadmin.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentUserListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        val adapter = UserAdapter()
        binding.userRv.layoutManager = LinearLayoutManager(requireActivity())
        binding.userRv.adapter = adapter
        userViewModel.getAllUser().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        return binding.root
    }

}