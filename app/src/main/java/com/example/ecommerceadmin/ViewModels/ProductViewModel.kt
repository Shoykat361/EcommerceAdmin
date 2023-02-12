package com.example.ecommerceadmin.ViewModels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceadmin.models.Product
import com.example.ecommerceadmin.models.Purchase
import com.example.ecommerceadmin.repos.ProductRepository
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class ProductViewModel : ViewModel(){
    val repository=ProductRepository()
    val productListLD:MutableLiveData<List<Product>> = MutableLiveData()
    val purchaseListLD:MutableLiveData<List<Purchase>> = MutableLiveData()
    val errMsgLD = MutableLiveData<String>()
    val statusLD = MutableLiveData<String>()

    fun getAllCategories():LiveData<List<String>>{
        return repository.getAllCategories()
    }
    fun uploadImage(bitmap: Bitmap, callback: (String) -> Unit) {
        val photoRef = FirebaseStorage.getInstance().reference
            .child("images/${System.currentTimeMillis()}")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos)
        val data: ByteArray = baos.toByteArray()
        val uploadTask = photoRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                callback(downloadUri)
            } else {
                errMsgLD.value = "could not save, please check your internet connection"
            }
        }
    }
    fun addNewProduct(product: Product,purchase: Purchase,callback:(String)->Unit){
        repository.addNewProduct(product,purchase,callback)

    }
    fun addRepurchase(purchase: Purchase)=repository.addRepurchase(purchase)
    fun getAllProduct()=repository.getAllproduct()
    fun getproductById(id:String)=repository.getproductById(id)
    fun getPurchaseListbyProductId(id:String)=repository.getPurchaseByProductId(id)

}