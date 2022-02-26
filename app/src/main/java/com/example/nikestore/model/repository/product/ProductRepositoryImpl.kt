package com.example.nikestore.model.repository.product

import com.example.nikestore.model.dataclass.Banner
import com.example.nikestore.model.dataclass.Product
import com.example.nikestore.model.datasource.product.ProductDataSource
import com.example.nikestore.model.datasource.product.ProductLocalDataSource
import com.example.nikestore.model.datasource.product.ProductRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val productRemoteDataSource: ProductDataSource ,private val productLocalDataSource: ProductDataSource):ProductRepository {
    override suspend fun getProducts(sort:String): Flow<List<Product>> {
        return flow {
            emit(productRemoteDataSource.getProducts(sort))
        }
    }

    override suspend fun getFavoriteProducts(): Flow<List<Product>> {
        return flow {
            emit(productLocalDataSource.getFavoriteProducts())
        }
    }

    override suspend fun addToFavorites(product: Product) = productLocalDataSource.addToFavorites(product)

    override suspend fun deleteFromFavorites(product: Product)  = productLocalDataSource.deleteFromFavorites(product)

    override suspend fun getBanners(): Flow<List<Banner>>{
        return flow {
            emit(productRemoteDataSource.getBanners())
        }
    }
}