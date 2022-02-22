package com.example.nikestore.model.dataclass

data class SubmitOrderResult(
    val bank_gateway_url: String,
    val order_id: Int
)