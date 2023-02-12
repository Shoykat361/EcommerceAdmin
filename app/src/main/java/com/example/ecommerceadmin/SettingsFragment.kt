package com.example.ecommerceadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ecommerceadmin.ViewModels.OrderViewModel
import com.example.ecommerceadmin.ViewModels.ProductViewModel
import com.example.ecommerceadmin.databinding.FragmentSettingsBinding
import com.example.ecommerceadmin.utlis.showInputDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val productViewModel: ProductViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        orderViewModel.getOrderSettings().observe(viewLifecycleOwner) {
            binding.settings = it
        }
        binding.deliveryChargeSetBtn.setOnClickListener {
            showInputDialog(requireContext(), title = "Delivery Charge") {
                val deliveryCharge = it.toDouble()
                orderViewModel.updateOrderSettingsField("deliveryCharge", deliveryCharge)
            }
        }
        binding.discountSetBtn.setOnClickListener {
            showInputDialog(requireContext(), title = "Discount") {
                val discount = it.toInt()
                orderViewModel.updateOrderSettingsField("discount", discount)
            }
        }
        binding.vatSetBtn.setOnClickListener {
            showInputDialog(requireContext(), title = "Vat") {
                val vat = it.toInt()
                orderViewModel.updateOrderSettingsField("vat", vat)
            }
        }
        return binding.root
    }

}