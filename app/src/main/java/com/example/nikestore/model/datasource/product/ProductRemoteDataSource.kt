package com.example.nikestore.model.datasource.product

import com.example.nikestore.model.apiService.ApiService
import com.example.nikestore.model.dataclass.Banner
import com.example.nikestore.model.dataclass.Product
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) : ProductDataSource {
    override suspend fun getProducts(sort:String): List<Product> {
       return apiService.getProduct(sort)
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

    override suspend fun getBanners(): List<Banner>  = apiService.getBanners()
}