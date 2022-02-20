package com.example.nikestore.di

import com.example.nikestore.model.`object`.TokenContainer
import com.example.nikestore.model.apiService.ApiService
import com.example.nikestore.utils.variables.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient():OkHttpClient{
        return  OkHttpClient.Builder()
            .addInterceptor {
                val oldRequest = it.request()
                val newRequest = oldRequest.newBuilder()
                if (TokenContainer.token != null){
                    newRequest.addHeader("Authorization", "Bearer ${TokenContainer.token}")
                }
                newRequest.addHeader("Accept", "application/json")
                newRequest.method(oldRequest.method, oldRequest.body)
                return@addInterceptor it.proceed(newRequest.build())
            }.addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()
    }
    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient):ApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

}