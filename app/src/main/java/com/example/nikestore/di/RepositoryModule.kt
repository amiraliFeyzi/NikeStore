package com.example.nikestore.di

import android.content.SharedPreferences
import com.example.nikestore.model.apiService.ApiService
import com.example.nikestore.model.datasource.cart.CartRemoteDataSource
import com.example.nikestore.model.datasource.comment.CommentRemoteDataSource
import com.example.nikestore.model.datasource.product.ProductLocalDataSource
import com.example.nikestore.model.datasource.product.ProductRemoteDataSource
import com.example.nikestore.model.datasource.user.UserLocalDataSource
import com.example.nikestore.model.datasource.user.UserRemoteDataSource
import com.example.nikestore.model.repository.cart.CartRepository
import com.example.nikestore.model.repository.cart.CartRepositoryImpl
import com.example.nikestore.model.repository.comment.CommentRepository
import com.example.nikestore.model.repository.comment.CommentRepositoryImpl
import com.example.nikestore.model.repository.product.ProductRepository
import com.example.nikestore.model.repository.product.ProductRepositoryImpl
import com.example.nikestore.model.repository.user.UserRepository
import com.example.nikestore.model.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(apiService: ApiService):ProductRepository{
        return ProductRepositoryImpl(ProductRemoteDataSource(apiService) , ProductLocalDataSource())
    }
    @Singleton
    @Provides
    fun provideCommentRepository(apiService: ApiService):CommentRepository{
        return CommentRepositoryImpl(CommentRemoteDataSource(apiService))
    }

    @Singleton
    @Provides
    fun provideCartRepository(apiService: ApiService):CartRepository{
        return CartRepositoryImpl(CartRemoteDataSource(apiService))
    }

    @Provides
    @Singleton
    fun provideUserLocalDataSource(sharedPreferences: SharedPreferences):UserLocalDataSource{
        return UserLocalDataSource(sharedPreferences)
    }
    @Singleton
    @Provides
    fun provideUserRepository(apiService: ApiService , sharedPreferences: SharedPreferences):UserRepository{
        return UserRepositoryImpl(UserRemoteDataSource(apiService) , UserLocalDataSource(sharedPreferences))
    }
}