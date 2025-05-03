package com.insecureshop.feature.product

import com.insecureshop.feature.webview.DEFAULT_HOST

data class ProductDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: String,
    val rating: Int,
    val url: String = DEFAULT_HOST,
    val qty: Int = 0,
)
