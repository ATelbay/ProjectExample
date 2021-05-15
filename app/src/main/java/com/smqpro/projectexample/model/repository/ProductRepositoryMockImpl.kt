package com.smqpro.projectexample.model.repository

import com.smqpro.projectexample.model.dao.ProductDao
import com.smqpro.projectexample.model.dto.Product

class ProductRepositoryMockImpl(private val productDao: ProductDao) : ProductRepository {

    override fun getCartList() = productDao.selectAllInCart()
    override fun getProductList() = productDao.selectAll()

    override suspend fun insertProductList(productList: List<Product>) =
        productDao.insertList(productList)

    override suspend fun insertProductItem(product: Product) = productDao.insertOrReplaceItem(product).toInt()

    override suspend fun countItemUp(id: Int) = productDao.countItemUp(id)
    override suspend fun countItemDown(id: Int) = productDao.countItemDown(id)
    override suspend fun setAllItemCountToZero() = productDao.setAllItemCountToZero()
    override suspend fun setItemCountToZero(id: Int) = productDao.setItemCountToZero(id)

    override suspend fun deleteProductItem(product: Product) = productDao.deleteItem(product)
}