package com.example.ecommerceadmin.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.ecommerceadmin.R
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:setImageUrl")
fun setImgUrl(imgView:ImageView,url :String?){
    url?.let {
        Glide.with(imgView.context)
            .load(url)
            .placeholder(R.drawable.img)
            .into(imgView)
    }

}
@BindingAdapter("app:setDate")
fun setDate(textView: TextView, timestamp: Timestamp) {
    textView.text = SimpleDateFormat("dd/MM/yyyy hh:mm a")
        .format(Date(timestamp.seconds * 1000))
}
@BindingAdapter("app:setDateWithText")
fun setDateWithText(textView: TextView, timestamp: Long) {
    textView.text = "Last seen on ${
        SimpleDateFormat("MMM dd, yyyy HH:mm")
        .format(Date(timestamp))}"
}