package com.smqpro.projectexample.model.dao

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.room.*
import com.smqpro.projectexample.model.dto.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertItem(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceItem(product: Product): Long

    @Delete
    suspend fun deleteItem(product: Product)

    @Query("SELECT * FROM product_table")
    fun selectAll(): Flow<List<Product>>

    @Query("SELECT * FROM product_table WHERE count > 0")
    fun selectAllInCart(): Flow<List<Product>>

    @Query("UPDATE product_table SET count = 0 WHERE id = :id")
    suspend fun setItemCountToZero(id: Int)

    @Query("UPDATE product_table SET count = count + 1 WHERE id = :id")
    suspend fun countItemUp(id: Int)

    @Query("UPDATE product_table SET count = count - 1 WHERE id = :id")
    suspend fun countItemDown(id: Int)

    @Query("UPDATE product_table SET title = :title, type = :type WHERE id = :id")
    suspend fun updateItemIgnoringCount(id: Int, title: String, type: Product.ProductType)

    @Query("SELECT * FROM product_table WHERE id = :id")
    suspend fun getItem(id: Int): Product

    @Query("UPDATE product_table SET count = 0")
    suspend fun setAllItemCountToZero()

    @Transaction
    suspend fun insertList(productList: List<Product>) {
        productList.forEach {
            try { insertItem(it) }
            catch (exception: SQLiteConstraintException) {
                updateItemIgnoringCount(it.id, it.title, it.type)
            }
        }
    }
}