package com.smqpro.projectexample.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smqpro.projectexample.model.repository.ProductRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelProviderFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = MainViewModel(repository) as T
}