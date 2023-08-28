package com.example.akakcecaseandroid.viewmodel

import androidx.lifecycle.ViewModel
import com.example.akakcecaseandroid.model.ResponseState
import com.example.akakcecaseandroid.model.repo.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {
    private val _responseFlow : MutableStateFlow<ResponseState> = MutableStateFlow(ResponseState.Empty)
    val responseFlow : StateFlow<ResponseState> = _responseFlow

    suspend fun fetchProducts(url: String) {
        apiRepository.getProductResultForListing(url).collect{responseState->
            _responseFlow.value = responseState
            println("responseflow: ${responseFlow.value}")
        }
    }
}