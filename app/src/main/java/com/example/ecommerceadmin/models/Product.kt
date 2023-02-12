package com.example.ecommerceadmin.models

data class Product(
    var id :String?=null,
    var name:String?=null,
    var category:String?=null,
    var description:String?=null,
    var salesPrice:Double?=0.0,
    var imgUrl:String?=null,
    var isAvailable:Boolean=true,
)
