package com.example.ecommerceadmin.repos

import android.view.ScrollCaptureCallback
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceadmin.models.Product
import com.example.ecommerceadmin.models.Purchase
import com.example.ecommerceadmin.utlis.collectionCategory
import com.example.ecommerceadmin.utlis.collectionProduct
import com.example.ecommerceadmin.utlis.collectionPurchase
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository {
    private val db =FirebaseFirestore.getInstance()
    fun addNewProduct(product: Product,purchase: Purchase,callback:(String)->Unit){
        val wb =db.batch()
        val productDocument =db.collection(collectionProduct).document()
        val purchaseDocument=db.collection(collectionPurchase).document()
        product.id=productDocument.id
        purchase.purchaseId=purchaseDocument.id
        purchase.productId=product.id
        wb.set(productDocument,product)
        wb.set(purchaseDocument,purchase)
        wb.commit().addOnSuccessListener {
            callback("Success")

        }.addOnFailureListener {
            callback("Failure")

        }

    }
    fun addRepurchase(purchase: Purchase){
        val purChaseDoc =db.collection(collectionPurchase).document()
        purchase.purchaseId=purChaseDoc.id
        purChaseDoc.set(purchase)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }

    }
    fun getAllproduct():LiveData<List<Product>>{

        val productLD =MutableLiveData<List<Product>>()
        db.collection(collectionProduct)
            .addSnapshotListener { value, error ->
                if (error != null){
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<Product>()
                for (doc in value!!.documents){
                    doc.toObject(Product ::class.java)?.let { tempList.add(it) }
                }
                productLD.value=tempList
            }
        return productLD


    }
    fun getproductById(id:String):LiveData<Product>{

        val productLD =MutableLiveData<Product>()
        db.collection(collectionProduct).document(id)
            .addSnapshotListener { value, error ->
                if (error != null){
                    return@addSnapshotListener
                }
                productLD.value=value?.toObject(Product::class.java)
            }
        return productLD
    }
    fun getPurchaseByProductId(id:String):LiveData<List<Purchase>> {

        val purchaseLD =MutableLiveData<List<Purchase>>()
        db.collection(collectionPurchase)
            .whereEqualTo("productId",id)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<Purchase>()
                for (doc in value!!.documents) {
                    doc.toObject(Purchase::class.java)?.let { tempList.add(it) }
                }
                purchaseLD.value = tempList
            }
        return purchaseLD

    }
    fun getAllCategories():LiveData<List<String>>{
        val catLd = MutableLiveData<List<String>>()
        db.collection(collectionCategory)
            .addSnapshotListener { value, error ->
                if (error != null){
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<String>()
                for (doc in value!!.documents){
                    tempList.add(doc.get("name").toString())
                }
                catLd.value=tempList
            }
        return catLd
    }
}