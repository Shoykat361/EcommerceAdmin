package com.example.ecommerceadmin.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceadmin.utlis.collectionAdmin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {
    enum class AuthState {
        AUTHENTICATED, UNAUTHENTICATED
    }
    val firebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val authStateLD: MutableLiveData<AuthState> = MutableLiveData()
    val errMsgLD: MutableLiveData<String> = MutableLiveData()

    init {
        if (firebaseAuth.currentUser != null) {
            authStateLD.value = AuthState.AUTHENTICATED
        } else {
            authStateLD.value = AuthState.UNAUTHENTICATED
        }
    }

    fun loginAdmin(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                val uid = it.user!!.uid
                db.collection(collectionAdmin).document(uid).get()
                    .addOnSuccessListener {
                        if (it.exists()) {
                            authStateLD.value = AuthState.AUTHENTICATED
                        } else {
                            errMsgLD.value = "You are not an Admin"
                            firebaseAuth.signOut()
                        }
                    }
            }.addOnFailureListener {
                errMsgLD.value = it.localizedMessage
            }
    }
}