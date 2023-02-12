package com.example.ecommerceadmin.models

import com.example.ecommerceadmin.utlis.OrderStatus
import com.example.ecommerceadmin.utlis.PaymentMethod
import com.google.firebase.Timestamp

data class Order(
    var orderId:String?=null,
    var userId:String?=null,
    var orderDate:Timestamp?=null,
    var delivaryCharge:Int?=0,
    var discount:Int?=0,
    var vat:Int?=0,
    var delivery_address:String?=null,
    var orderStatus:String= OrderStatus.pending,
    var paymentMethod:String=PaymentMethod.cod,

)
