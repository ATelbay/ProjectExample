package com.smqpro.projectexample.model.repository

import com.smqpro.projectexample.model.dto.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getCardList(): Flow<List<Product>>
    suspend fun fetchProductList(): List<Product>
    suspend fun deleteCartItem(product: Product)
    suspend fun insertCartItem(product: Product)
}