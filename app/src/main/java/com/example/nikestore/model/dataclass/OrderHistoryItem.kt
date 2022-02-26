package com.example.nikestore.model.dataclass

data class OrderHistoryItem(
    val id: Int,
    val payable: Int,
    val order_items: List<OrderItem>,

)