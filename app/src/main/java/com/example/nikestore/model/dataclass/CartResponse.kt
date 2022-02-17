package com.example.nikestore.model.dataclass

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)

