package com.example.nikestore.model.repository.order

import com.example.nikestore.model.dataclass.Checkout
import com.example.nikestore.model.dataclass.OrderHistoryItem
import com.example.nikestore.model.dataclass.SubmitOrderResult
import com.example.nikestore.model.datasource.order.OrderDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl (private val orderRemoteDataSource: OrderDataSource):OrderRepository {
    override suspend fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String,
    ): Flow<SubmitOrderResult> {
       return flow {
           emit(orderRemoteDataSource.submit(firstName, lastName, postalCode, phoneNumber, address, paymentMethod))
       }

    }

    override suspend fun checkout(orderId: Int): Flow<Checkout> {
        return flow {
            emit(orderRemoteDataSource.checkout(orderId))
        }

    }

    override suspend fun listOrders(): Flow<List<OrderHistoryItem>> {
        return flow {
            emit(orderRemoteDataSource.listOrders())
        }
    }
}