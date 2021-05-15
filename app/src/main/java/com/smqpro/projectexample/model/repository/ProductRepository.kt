package com.smqpro.projectexample.model.repository

import com.smqpro.projectexample.model.dto.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getCartList(): Flow<List<Product>>
    fun getProductList(): Flow<List<Product>>

    suspend fun insertProductList(productList: List<Product>)
    suspend fun insertProductItem(product: Product): Int

    suspend fun countItemUp(id: Int)
    suspend fun countItemDown(id: Int)
    suspend fun setItemCountToZero(id: Int)
    suspend fun setAllItemCountToZero()
    suspend fun deleteProductItem(product: Product)
}