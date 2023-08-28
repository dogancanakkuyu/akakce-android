package com.example.akakcecaseandroid.model.repo

import com.example.akakcecaseandroid.model.ResponseState
import com.example.akakcecaseandroid.model.data.Root
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    fun getProductResultForListing(url: String): Flow<ResponseState>
    fun getProductDetail(url: String,code: Int): Flow<ResponseState>
}