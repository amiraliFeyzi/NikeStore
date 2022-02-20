package com.example.nikestore.model.datasource.cart

import com.example.nikestore.model.apiService.ApiService
import com.example.nikestore.model.dataclass.AddToCartResponse
import com.example.nikestore.model.dataclass.CartItemCount
import com.example.nikestore.model.dataclass.CartResponse
import com.example.nikestore.model.dataclass.MessageResponse
import com.google.gson.JsonObject
import javax.inject.Inject

class CartRemoteDataSource @Inject constructor(val apiService: ApiService) :CartDataSource {
    override suspend fun addToCart(productId: Int): AddToCartResponse {
        return apiService.addToCart(JsonObject().apply {
            addProperty("product_id" , productId)
        })
    }

    override suspend fun get(): CartResponse  = apiService.getCart()
    override suspend fun remove(cartItemId: Int): MessageResponse  = apiService.removeItemFromCart(JsonObject().apply {
        addProperty("cart_item_id" , cartItemId)
    })

    override suspend fun changeCount(cartItemId: Int, count: Int): AddToCartResponse  = apiService.changeCount(JsonObject().apply {
        addProperty("cart_item_id", cartItemId)
        addProperty("count", count)
    })
    override  suspend fun getCartItemsCount(): CartItemCount  = apiService.getCartItemsCount()
}