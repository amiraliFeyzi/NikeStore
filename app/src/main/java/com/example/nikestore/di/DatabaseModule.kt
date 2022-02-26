package com.example.nikestore.di

import android.content.Context
import androidx.room.Room
import com.example.nikestore.model.database.AppDatabase
import com.example.nikestore.model.database.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase( @ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(context , AppDatabase::class.java , "db_app").build()
    }


    @Provides
    @Singleton
    fun provideProductDao(appDatabase: AppDatabase):ProductDao{
        return appDatabase.productDao()
    }

}