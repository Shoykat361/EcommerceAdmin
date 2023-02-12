package com.example.ecommerceadmin

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.ecommerceadmin.CustomDialogues.DatePickerFragment
import com.example.ecommerceadmin.ViewModels.ProductViewModel
import com.example.ecommerceadmin.databinding.FragmentAddProductBinding
import com.example.ecommerceadmin.models.Product
import com.example.ecommerceadmin.models.Purchase
import com.example.ecommerceadmin.utlis.getFormattedDate
import com.google.firebase.Timestamp

class AddProductFragment : Fragment() {
    private val productViewModel:ProductViewModel by activityViewModels()
    private lateinit var binding :FragmentAddProductBinding
    private var category:String? =null
    private var timestamp :Timestamp?=null
    private var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddProductBinding.inflate(inflater,container,false)
        productViewModel.getAllCategories().observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                val spAdapter =ArrayAdapter<String>(requireActivity(),android.R.layout.simple_spinner_dropdown_item,it)
                binding.catSp.adapter=spAdapter
            }

        }
        productViewModel.errMsgLD.observe(viewLifecycleOwner){
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        binding.catSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected
                        (p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                category=p0!!.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }
        binding.dateBtn.setOnClickListener {
            DatePickerFragment{
                timestamp=it
                binding.dateBtn.text= getFormattedDate(timestamp!!.seconds*1000,"dd/MM/yyyy")
            }.show(childFragmentManager,null)

        }
        binding.captureBtn.setOnClickListener {
            dispatchTakePictureIntent()
        }
        binding.saveBtn.setOnClickListener {
            saveProduct()
        }
        return binding.root
    }

    private fun saveProduct() {
        val name = binding.nameInputEt.text.toString()
        val description =binding.descriptionInputEt.text.toString()
        val purchasePrice=binding.purchasepriceInputEt.text.toString()
        val salePrice=binding.salepriceInputEt.text.toString()
        val quantity=binding.quantityInputEt.text.toString()
        binding.mProgressBar.visibility=View.VISIBLE
        productViewModel.uploadImage(bitmap!!){
            val product = Product(
                name=name,
                description = description,
                salesPrice = salePrice.toDouble(),
                category = category,
                imgUrl = it
            )
            val purchase =Purchase(
                purchasePrice=purchasePrice.toDouble(),
                quantity = quantity.toDouble(),
                purchaseDate = timestamp,
            )
            productViewModel.addNewProduct(product,purchase){
                if (it == "Success"){
                    resetField()
                    binding.mProgressBar.visibility=View.GONE
                }else{
                    binding.mProgressBar.visibility=View.GONE
                    Toast.makeText(requireActivity(), "Could Not save", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

    private fun resetField() {
        binding.nameInputEt.text=null
        binding.descriptionInputEt.text=null
        binding.purchasepriceInputEt.text=null
        binding.salepriceInputEt.text=null
        binding.quantityInputEt.text=null
    }

    val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            bitmap = it.data?.extras?.get("data") as Bitmap
            binding.productIv.setImageBitmap(bitmap)
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            resultLauncher.launch(takePictureIntent)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

}