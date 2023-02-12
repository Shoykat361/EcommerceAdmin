package com.example.ecommerceadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceadmin.ViewModels.LoginViewModel
import com.example.ecommerceadmin.adapters.DashBoardAdapter
import com.example.ecommerceadmin.databinding.FragmentDashBoardBinding
import com.example.ecommerceadmin.models.DashboardItemType

class DashBoardFragment : Fragment() {
   private lateinit var binding :FragmentDashBoardBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashBoardBinding.inflate(inflater,container,false)
        loginViewModel.authStateLD.observe(viewLifecycleOwner) {
            if (it == LoginViewModel.AuthState.UNAUTHENTICATED) {
                findNavController().navigate(R.id.action_dashBoardFragment_to_loginFragment)
            }
        }
        val adapter =DashBoardAdapter {
            navigateToItem(it)
        }
        val glm =GridLayoutManager(requireActivity(),2)
        binding.dashBoardRv.layoutManager=glm
        binding.dashBoardRv.adapter=adapter


        return binding.root
    }

    private fun navigateToItem(it: DashboardItemType) {
        when(it){
            DashboardItemType.ADD_PRODUCT -> findNavController().navigate(R.id.action_dashBoardFragment_to_addProductFragment)
            DashboardItemType.VIEW_PRODUCT ->findNavController().navigate(R.id.action_dashBoardFragment_to_viewProductFragment)
            DashboardItemType.CATEGORY -> findNavController().navigate(R.id.action_dashBoardFragment_to_categoryProductFragment)
            DashboardItemType.USER -> findNavController().navigate(R.id.action_dashBoardFragment_to_userListFragment)
            DashboardItemType.SETTING -> findNavController().navigate(R.id.action_dashBoardFragment_to_settingsFragment)

        }
    }

}