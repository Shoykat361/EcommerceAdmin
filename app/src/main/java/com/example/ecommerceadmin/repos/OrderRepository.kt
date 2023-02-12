package com.example.ecommerceadmin.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceadmin.models.Order
import com.example.ecommerceadmin.models.OrderSetting
import com.example.ecommerceadmin.utlis.collectionOrder
import com.example.ecommerceadmin.utlis.collectionOrderSettings
import com.example.ecommerceadmin.utlis.documentOrderConstants
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class OrderRepository @Inject constructor(val db: FirebaseFirestore){

    fun getOrderSettings() : LiveData<OrderSetting> {
        val settingsLD = MutableLiveData<OrderSetting>()
        db.collection(collectionOrderSettings)
            .document(documentOrderConstants)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                settingsLD.value = value!!.toObject(OrderSetting::class.java)
            }
        return settingsLD
    }

    fun updateOrderSettingsField(field: String, value: Any) {
        db.collection(collectionOrderSettings).document(documentOrderConstants)
            .update(field, value)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    fun getAllOrders() : LiveData<List<Order>> {
        val orderLD = MutableLiveData<List<Order>>()
        db.collection(collectionOrder)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val tempList = mutableListOf<Order>()
                for (doc in value!!.documents) {
                    doc.toObject(Order::class.java)?.let { tempList.add(it) }
                }
                orderLD.value = tempList
            }
        return orderLD
    }
}