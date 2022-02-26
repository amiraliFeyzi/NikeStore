package com.example.nikestore.model.datasource.product

import com.example.nikestore.model.dataclass.Banner
import com.example.nikestore.model.dataclass.Product

interface ProductDataSource {
    suspend fun getProducts(sort:String): List<Product>

    suspend fun searchProduct(name:String):List<Product>

    suspend fun getFavoriteProducts(): List<Product>

    suspend fun addToFavorites(product: Product)

    suspend fun deleteFromFavorites(product: Product)

    suspend fun getBanners():List<Banner>
}