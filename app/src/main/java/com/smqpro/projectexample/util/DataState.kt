package com.smqpro.projectexample.util

sealed class DataState<out T> {
    object Empty: DataState<Nothing>()
    object Loading: DataState<Nothing>()
    data class Success<T>(val data: T): DataState<T>()
    data class Error<T>(val message: String, val data: T? = null): DataState<T>()
}