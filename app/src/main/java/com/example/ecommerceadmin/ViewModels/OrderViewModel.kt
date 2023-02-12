package com.example.ecommerceadmin.ViewModels

import androidx.lifecycle.ViewModel
import com.example.ecommerceadmin.models.OrderSetting
import com.example.ecommerceadmin.repos.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(val orderRepository: OrderRepository) : ViewModel() {

    fun getOrderSettings() = orderRepository.getOrderSettings()

    fun getAllOrders(userId: String) = orderRepository.getAllOrders()

    fun updateOrderSettingsField(field: String, value: Any) = orderRepository.updateOrderSettingsField(field, value)

    fun getDiscountAmount(total: Double, settings: OrderSetting) : Double {
        return (total * settings.discount) / 100
    }

    fun getVatAmount(total: Double, settings: OrderSetting) : Double {
        val priceAfterDiscount = total - getDiscountAmount(total, settings)
        return (priceAfterDiscount * settings.vat) / 100
    }

    fun getGrandTotal(total: Double, settings: OrderSetting) : Double{
        val priceAfterDiscount = total - getDiscountAmount(total, settings)
        return priceAfterDiscount + getVatAmount(total, settings) + settings.delivaryCharge
    }

}