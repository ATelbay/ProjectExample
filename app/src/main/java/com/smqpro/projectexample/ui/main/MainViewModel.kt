package com.smqpro.projectexample.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smqpro.projectexample.model.dto.Product
import com.smqpro.projectexample.model.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository) : ViewModel() {

    val productList = repository.getProductList()
    val cartList = repository.getCartList()

    fun countItemUp(id: Int) = viewModelScope.launch(Dispatchers.IO) { repository.countItemUp(id) }
    fun countItemDown(id: Int) =
        viewModelScope.launch(Dispatchers.IO) { repository.countItemDown(id) }

    fun setItemCountToZero(id: Int) =
        viewModelScope.launch(Dispatchers.IO) { repository.setItemCountToZero(id) }

    fun setAllItemCountToZero() =
        viewModelScope.launch(Dispatchers.IO) { repository.setAllItemCountToZero() }

    fun insertProduct(product: Product): Int {
        var ar = 0
        viewModelScope.launch(Dispatchers.IO) { ar = repository.insertProductItem(product) }
        return ar
    }

    fun deleteProduct(product: Product) =
        viewModelScope.launch(Dispatchers.IO) { repository.deleteProductItem(product) }


}