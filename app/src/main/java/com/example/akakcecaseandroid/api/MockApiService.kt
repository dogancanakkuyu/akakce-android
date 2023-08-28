package com.example.akakcecaseandroid.api

import com.example.akakcecaseandroid.model.data.ProductDetailResult
import com.example.akakcecaseandroid.model.data.Root
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MockApiService {
    @GET
    suspend fun getProductResult(@Url url: String) : Response<Root>
    @GET
    suspend fun getProductDetail(@Url url: String,@Query("code") code: Int ) : Response<ProductDetailResult>
}