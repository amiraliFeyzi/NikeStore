package com.example.nikestore.model.repository.cart

import com.example.nikestore.model.dataclass.AddToCartResponse
import com.example.nikestore.model.dataclass.CartItemCount
import com.example.nikestore.model.dataclass.CartResponse
import com.example.nikestore.model.dataclass.MessageResponse
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun addToCart(productId: Int): AddToCartResponse
    suspend fun get(): Flow<CartResponse>
    suspend fun remove(cartItemId: Int): MessageResponse
    suspend fun changeCount(cartItemId: Int, count: Int):AddToCartResponse
    suspend fun getCartItemsCount(): Flow<CartItemCount>
}