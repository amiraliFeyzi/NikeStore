package com.example.nikestore.model.datasource.order

import com.example.nikestore.model.dataclass.Checkout
import com.example.nikestore.model.dataclass.OrderHistoryItem
import com.example.nikestore.model.dataclass.SubmitOrderResult

interface OrderDataSource {

    suspend fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): SubmitOrderResult

    suspend fun checkout(orderId: Int): Checkout

    suspend fun listOrders():List<OrderHistoryItem>
}