package com.example.ecommerceadmin.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceadmin.models.UserofEcom
import com.example.ecommerceadmin.utlis.collectionUser
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    val db = FirebaseFirestore.getInstance()

    fun getUser(userId: String): LiveData<UserofEcom> {
        val userLD = MutableLiveData<UserofEcom>()
        db.collection(collectionUser)
            .document(userId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                userLD.value = value!!.toObject(UserofEcom::class.java)
            }
        return userLD
    }

    fun getAllUser() : LiveData<List<UserofEcom>> {
        val userLD = MutableLiveData<List<UserofEcom>>()
        db.collection(collectionUser)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val temp = mutableListOf<UserofEcom>()
                for (doc in value!!.documents) {
                    doc.toObject(UserofEcom::class.java)?.let { temp.add(it) }
                }
                userLD.value = temp
            }
        return userLD
    }
}