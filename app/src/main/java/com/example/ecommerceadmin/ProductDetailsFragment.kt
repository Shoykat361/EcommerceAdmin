package com.example.ecommerceadmin

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ecommerceadmin.CustomDialogues.DatePickerFragment
import com.example.ecommerceadmin.ViewModels.ProductViewModel
import com.example.ecommerceadmin.databinding.FragmentProductDetailsBinding
import com.example.ecommerceadmin.databinding.RepurchseLayoutBinding
import com.example.ecommerceadmin.models.Purchase
import com.example.ecommerceadmin.utlis.getFormattedDate


class ProductDetailsFragment : Fragment() {
    private lateinit var binding:FragmentProductDetailsBinding
    private val productViewModel:ProductViewModel by activityViewModels()
    private var id:String?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        id = arguments?.getString("id")
        id?.let {
            productViewModel.getproductById(it).observe(viewLifecycleOwner) {
                binding.product = it
            }
        }
        id?.let {
            productViewModel.getPurchaseListbyProductId(it).observe(viewLifecycleOwner) {
                var purchaseHistory = ""
                it.forEach {
                    //binding.purchaseHistoryTv.text= getFormattedDate(it.purchaseDate!!.seconds*1000,"dd/MM/yyyy")

                    purchaseHistory += "${getFormattedDate(it.purchaseDate!!.seconds * 1000, "dd/MM/yyyy")} - Qty ${it.quantity} - Price: ${it.purchasePrice}\n"
                }
                binding.purchaseHistoryTv.text = purchaseHistory
            }
        }
        binding.rePurchaseBtn.setOnClickListener {
            showRepurchaseDialogue()
        }

            return binding.root
        }

    private fun showRepurchaseDialogue() {
        var purhaseTime :com.google.firebase.Timestamp?=null
        val builder =AlertDialog.Builder(requireActivity()).apply {
            setTitle("Repuchase Product")
            val relbinding =RepurchseLayoutBinding.inflate(LayoutInflater.from(requireContext()))
            setView(relbinding.root)
            relbinding.rePurchaseDateBtn.setOnClickListener {
                DatePickerFragment{
                    purhaseTime=it
                    relbinding.rePurchaseDateBtn.text= getFormattedDate(it.seconds*1000,"dd/MM/yyyy")

                }.show(childFragmentManager,null)
            }
            setPositiveButton("Buy"){dialogue,value ->
                val qty =relbinding.rePuchaseQuantityEt.text.toString()
                val price = relbinding.rePuchaseEt.text.toString()
                val purchase = Purchase(
                    productId = id,
                    purchaseDate = purhaseTime,
                    purchasePrice = price.toDouble(),
                    quantity = qty.toDouble()

                )
                productViewModel.addRepurchase(purchase)

            }
            setNegativeButton("Close",null)
        }
        val dialog :AlertDialog=builder.create()
        dialog.show()
    }
}