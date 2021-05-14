package com.smqpro.projectexample.model.dao

import androidx.room.*
import com.smqpro.projectexample.model.dto.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(product: Product)

    @Delete
    suspend fun deleteItem(product: Product)

    @Query("SELECT * FROM product_table")
    fun selectAll(): Flow<List<Product>>
}