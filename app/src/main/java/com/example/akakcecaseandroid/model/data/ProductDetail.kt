package com.example.akakcecaseandroid.model.data

data class ProductDetail(
    val mkName: String,
    val productName: String,
    val badge: String,
    val rating: Double,
    var imageUrl: String,
    val storageOptions: List<String>,
    val countOfPrices: String,
    val price: Int,
    val freeShipping: Boolean,
    val lastUpdate: String
)

data class ProductDetailResult(
    val result: ProductDetail
)
