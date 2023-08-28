package com.example.akakcecaseandroid.model.data


data class Product(
    val code: Int,
    val imageUrl: String,
    val name: String,
    val dropRatio: Double?,
    val price: Double,
    val countOfPrices: Int?,
    val followCount: Int?,
)
