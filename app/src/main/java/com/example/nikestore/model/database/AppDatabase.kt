package com.example.nikestore.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nikestore.model.dataclass.Product

@Database(entities = [Product::class] , version = 1 , exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun productDao():ProductDao


}