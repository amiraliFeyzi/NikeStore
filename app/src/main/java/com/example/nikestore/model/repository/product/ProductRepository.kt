package com.example.nikestore.model.repository.product

import com.example.nikestore.model.dataclass.Banner
import com.example.nikestore.model.dataclass.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(sort:String): Flow<List<Product>>

    suspend fun getFavoriteProducts(): Flow<List<Product>>

    suspend fun addToFavorites(product: Product)

    suspend fun deleteFromFavorites(product: Product)

    suspend fun getBanners():Flow<List<Banner>>

}