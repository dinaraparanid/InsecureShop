package com.insecureshop.feature.product

data class ProductDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: String,
    val rating: Int,
    val url: String = "https://www.insecureshopapp.com",
    val qty: Int = 0,
)
