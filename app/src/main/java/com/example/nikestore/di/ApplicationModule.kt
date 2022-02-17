package com.example.nikestore.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.nikestore.components.imageview.FrescoImageLoading
import com.example.nikestore.components.imageview.ImageLoading
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideImageLoading():ImageLoading{
        return FrescoImageLoading()
    }

    @Singleton
    @Provides
    fun provideSharedPerformance(@ApplicationContext context: Context):SharedPreferences{
        return context.getSharedPreferences("app_info" ,MODE_PRIVATE)
    }
}