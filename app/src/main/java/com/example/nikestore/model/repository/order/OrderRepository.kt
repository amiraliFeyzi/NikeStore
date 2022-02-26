package com.example.nikestore.model.repository.order

import com.example.nikestore.model.dataclass.Checkout
import com.example.nikestore.model.dataclass.OrderHistoryItem
import com.example.nikestore.model.dataclass.SubmitOrderResult
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Flow<SubmitOrderResult>

    suspend fun checkout(orderId: Int): Flow<Checkout>

    suspend fun listOrders():Flow<List<OrderHistoryItem>>

}