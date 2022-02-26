package com.example.nikestore.model.database

import androidx.room.*
import com.example.nikestore.model.dataclass.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getFavoriteProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorites(product: Product)

    @Delete
    fun deleteFromFavorites(product: Product)

}