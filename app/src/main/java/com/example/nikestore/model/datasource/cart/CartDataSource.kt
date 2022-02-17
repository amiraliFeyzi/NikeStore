package com.example.nikestore.model.datasource.cart

import com.example.nikestore.model.dataclass.AddToCartResponse
import com.example.nikestore.model.dataclass.CartItemCount
import com.example.nikestore.model.dataclass.CartResponse
import com.example.nikestore.model.dataclass.MessageResponse

interface CartDataSource {

    suspend fun addToCart(productId: Int): AddToCartResponse
    suspend fun get(): CartResponse
    suspend fun remove(cartItemId: Int): MessageResponse
    suspend fun changeCount(cartItemId: Int, count: Int): AddToCartResponse
    suspend fun getCartItemsCount(): CartItemCount
}