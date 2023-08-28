package com.example.akakcecaseandroid.viewmodel

import androidx.lifecycle.ViewModel
import com.example.akakcecaseandroid.model.ResponseState
import com.example.akakcecaseandroid.model.repo.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {
    private val _detailResponse : MutableStateFlow<ResponseState> = MutableStateFlow(ResponseState.Empty)
    val detailResponse : StateFlow<ResponseState> = _detailResponse

    suspend fun fetchProductDetail(url: String,code: Int) {
        apiRepository.getProductDetail(url, code).collect{responseState->
            _detailResponse.value = responseState
        }
    }
}