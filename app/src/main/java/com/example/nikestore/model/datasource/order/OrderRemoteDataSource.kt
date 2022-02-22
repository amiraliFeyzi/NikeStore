package com.example.nikestore.model.datasource.order

import com.example.nikestore.model.apiService.ApiService
import com.example.nikestore.model.dataclass.Checkout
import com.example.nikestore.model.dataclass.SubmitOrderResult
import com.google.gson.JsonObject
import javax.inject.Inject

class OrderRemoteDataSource (private val apiService: ApiService):OrderDataSource {
    override suspend fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String,
    ): SubmitOrderResult {
        return apiService.submitOrder(JsonObject().apply {
            addProperty("first_name", firstName)
            addProperty("last_name", lastName)
            addProperty("postal_code", postalCode)
            addProperty("mobile", phoneNumber)
            addProperty("address", address)
            addProperty("payment_method", paymentMethod)
        })
    }

    override suspend fun checkout(orderId: Int): Checkout {
       return apiService.checkOut(orderId)
    }
}