package com.example.nikestore.model.repository.cart

import com.example.nikestore.model.dataclass.AddToCartResponse
import com.example.nikestore.model.dataclass.CartItemCount
import com.example.nikestore.model.dataclass.CartResponse
import com.example.nikestore.model.dataclass.MessageResponse
import com.example.nikestore.model.datasource.cart.CartDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CartRepositoryImpl (val cartRemoteDataSource: CartDataSource):CartRepository {

    override suspend fun addToCart(productId: Int): AddToCartResponse{
        return cartRemoteDataSource.addToCart(productId)

    }

    override suspend fun get(): Flow<CartResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun remove(cartItemId: Int): Flow<MessageResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun changeCount(cartItemId: Int, count: Int): Flow<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getCartItemsCount(): Flow<CartItemCount> {
        TODO("Not yet implemented")
    }
}