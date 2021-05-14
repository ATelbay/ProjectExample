package com.smqpro.projectexample.model.repository

import com.smqpro.projectexample.model.dao.ProductDao
import com.smqpro.projectexample.model.dto.Product

class ProductRepositoryMockImpl(private val productDao: ProductDao) : ProductRepository {
    override fun getCardList() = productDao.selectAll()
    override suspend fun fetchProductList() = Product.generateData()
    override suspend fun deleteCartItem(product: Product) = productDao.deleteItem(product)
    override suspend fun insertCartItem(product: Product) = productDao.insertItem(product)
}