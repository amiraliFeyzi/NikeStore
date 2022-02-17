package com.example.nikestore.model.dataclass


data class CartItem(
    val cart_item_id: Int,
    var count: Int,
    val product: Product,
)