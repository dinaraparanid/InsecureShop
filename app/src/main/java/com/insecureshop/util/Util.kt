package com.insecureshop.util

import com.insecureshop.BuildConfig
import com.insecureshop.feature.product.ProductDetail
import com.insecureshop.feature.webview.DEFAULT_HOST
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Util @Inject constructor(
    private val prefs: Prefs,
) {
    fun verifyUserNamePassword(username: String, password: String) =
        // In a real-life should be verified on server-side
        BuildConfig.USERNAME == username && BuildConfig.PASSWORD == password

    private fun getProductList() = listOf(
        // In a real-life should rather be loaded from the server
        ProductDetail(1, "Laptop", "https://images.pexels.com/photos/7974/pexels-photo.jpg", "80", 1, DEFAULT_HOST),
        ProductDetail(2, "Hat", "https://images.pexels.com/photos/984619/pexels-photo-984619.jpeg", "10", 2, DEFAULT_HOST),
        ProductDetail(3, "Sunglasses", "https://images.pexels.com/photos/343720/pexels-photo-343720.jpeg", "10", 4, DEFAULT_HOST),
        ProductDetail(4, "Watch", "https://images.pexels.com/photos/277390/pexels-photo-277390.jpeg", "30", 4, DEFAULT_HOST),
        ProductDetail(5, "Camera", "https://images.pexels.com/photos/225157/pexels-photo-225157.jpeg", "40", 2, DEFAULT_HOST),
        ProductDetail(6, "Perfumes", "https://images.pexels.com/photos/264819/pexels-photo-264819.jpeg", "10", 2, DEFAULT_HOST),
        ProductDetail(7, "Bagpack", "https://images.pexels.com/photos/532803/pexels-photo-532803.jpeg", "20", 2, DEFAULT_HOST),
        ProductDetail(8, "Jacket", "https://images.pexels.com/photos/789812/pexels-photo-789812.jpeg", "20", 2, DEFAULT_HOST),
    )

    fun storeProductList(productList: List<ProductDetail> = getProductList()) {
        prefs.storeProductList(productList)
    }

    fun loadProductList() = prefs.loadProductList() ?: getProductList()

    fun updateProductItem(updateProductDetail: ProductDetail): List<ProductDetail> {
        val newProductList = loadProductList().map { product ->
            when (product.id) {
                updateProductDetail.id -> updateProductDetail
                else -> product
            }
        }

        storeProductList(newProductList)
        return newProductList
    }

    fun getCartProduct() = loadProductList().filter { it.qty > 0 }
}
