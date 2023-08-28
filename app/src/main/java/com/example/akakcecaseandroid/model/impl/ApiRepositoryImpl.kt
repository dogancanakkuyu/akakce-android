package com.example.akakcecaseandroid.model.impl

import com.example.akakcecaseandroid.api.MockApiService
import com.example.akakcecaseandroid.model.ResponseState
import com.example.akakcecaseandroid.model.data.Root
import com.example.akakcecaseandroid.model.repo.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val retrofitInstance: MockApiService) : ApiRepository {
    override fun getProductResultForListing(url: String): Flow<ResponseState> = flow {
        val response = retrofitInstance.getProductResult(url)
        emit(ResponseState.Loading)
        if (response.isSuccessful) emit(ResponseState.Success(response.body()))
        else emit(ResponseState.Error(response.message()))
    }

    override fun getProductDetail(url: String, code: Int): Flow<ResponseState> = flow {
        val response = retrofitInstance.getProductDetail(url, code)
        emit(ResponseState.Loading)
        if (response.isSuccessful) emit(ResponseState.Success(response.body()))
        else emit(ResponseState.Error(response.message()))
    }
}