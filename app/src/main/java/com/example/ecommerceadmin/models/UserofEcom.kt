package com.example.ecommerceadmin.models

data class UserofEcom(
    /*var userId :String?=null,
    var userName:String?=null,
    var phone:String?=null,
    var address:String?=null,
    var image:String?=null,*/
    var userId: String? = null,
    var userName: String? = null,
    var emailAddress: String? = null,
    var userCreationTimeStamp: Long? = null,
    var userLastSignInTimeStamp: Long? = null,
    var phone: String? = null,
    var address: String? = null,
    var image: String? = null,
    var online: Boolean = false,
    var lastUsageTimestamp: Long? = null,
)
