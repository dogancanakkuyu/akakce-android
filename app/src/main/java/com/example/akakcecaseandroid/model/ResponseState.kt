package com.example.akakcecaseandroid.model

sealed class ResponseState {
    data class Success<T>(val data: T) : ResponseState()
    data class Error(val msg: String?) : ResponseState()
    object Loading : ResponseState()
    object Empty : ResponseState()
}
