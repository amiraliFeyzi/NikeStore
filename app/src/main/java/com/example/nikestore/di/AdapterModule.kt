package com.example.nikestore.di

import com.example.nikestore.components.imageview.ImageLoading
import com.example.nikestore.view.home.adapter.LatestProductListAdapter
import com.example.nikestore.view.home.adapter.BannerAdapter
import com.example.nikestore.view.home.adapter.PopularProductListAdapter
import com.example.nikestore.view.productlist.adapter.ProductListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AdapterModule {

    @Singleton
    @Provides
    fun provideProductListAdapter(imageLoading: ImageLoading):ProductListAdapter{
        return ProductListAdapter(imageLoading)
    }
}