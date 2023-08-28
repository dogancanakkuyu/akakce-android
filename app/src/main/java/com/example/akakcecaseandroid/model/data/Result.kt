package com.example.akakcecaseandroid.model.data

import com.example.akakcecaseandroid.model.data.Product

data class Result(
    val nextUrl: String?,
    val horizontalProducts: List<Product>?,
    val products: List<Product>,
)
