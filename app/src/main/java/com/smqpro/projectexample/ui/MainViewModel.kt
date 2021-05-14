package com.smqpro.projectexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smqpro.projectexample.model.dto.Product
import com.smqpro.projectexample.model.repository.ProductRepository
import com.smqpro.projectexample.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productList = MutableStateFlow<DataState<List<Product>>>(DataState.Empty)
    val productList get() = _productList as StateFlow<DataState<List<Product>>>

    val cartList = repository.getCardList()

    init {
        fetchProductList()
    }

    fun fetchProductList() = viewModelScope.launch(Dispatchers.IO) {
        _productList.emit(DataState.Loading)
        delay(500L)
        repository.fetchProductList().let {
            if (it.isEmpty())
                _productList.emit(DataState.Error("Произошла ошибка"))
            else
                _productList.emit(DataState.Success(it))
        }
    }

    fun deleteCartItem(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteCartItem(product)
    }

    fun insertCartItem(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCartItem(product)
    }


}