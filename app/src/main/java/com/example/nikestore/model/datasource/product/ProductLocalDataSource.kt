package com.example.nikestore.model.datasource.product

import com.example.nikestore.model.dataclass.Banner
import com.example.nikestore.model.dataclass.Product

class ProductLocalDataSource:ProductDataSource {
    override suspend fun getProducts(sort:String): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteProducts(): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorites(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromFavorites(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun getBanners(): List<Banner> {
        TODO("Not yet implemented")
    }
}