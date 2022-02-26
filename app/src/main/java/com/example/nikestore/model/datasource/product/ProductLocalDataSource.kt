package com.example.nikestore.model.datasource.product

import com.example.nikestore.model.database.ProductDao
import com.example.nikestore.model.dataclass.Banner
import com.example.nikestore.model.dataclass.Product

class ProductLocalDataSource (private val productDao: ProductDao):ProductDataSource {
    override suspend fun getProducts(sort:String): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun searchProduct(name: String): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteProducts(): List<Product> {
       return productDao.getFavoriteProducts()
    }

    override suspend fun addToFavorites(product: Product) = productDao.addToFavorites(product)


    override suspend fun deleteFromFavorites(product: Product) =
       productDao.deleteFromFavorites(product)


    override suspend fun getBanners(): List<Banner> {
        TODO("Not yet implemented")
    }
}