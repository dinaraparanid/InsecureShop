package com.insecureshop.util

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insecureshop.feature.product.ProductDetail
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_USERNAME = "username"
private const val KEY_PASSWORD = "password"
private const val KEY_URL = "url"
private const val KEY_PRODUCT_LIST = "product_list"

@Singleton
class Prefs @Inject constructor(
    @ApplicationContext context: Context,
    private val crypto: Crypto,
) {
    private val sharedPreferences by lazy {
        context.getSharedPreferences("Prefs", Context.MODE_PRIVATE)
    }

    private val gson by lazy { Gson() }

    private fun loadStringProperty(alias: String): String? =
        sharedPreferences
            .getString(alias, null)
            ?.let { crypto.decrypt(alias, it) }

    private fun storeStringProperty(alias: String, value: String) {
        val encrypted = crypto.encrypt(alias, value)
        sharedPreferences.edit { putString(alias, encrypted) }
    }

    fun loadUrl() = loadStringProperty(KEY_URL)

    fun storeUrl(url: String) = storeStringProperty(KEY_URL, url)

    fun loadUsername() = loadStringProperty(KEY_USERNAME)

    fun storeUsername(username: String) = storeStringProperty(KEY_USERNAME, username)

    fun loadPassword() = loadStringProperty(KEY_PASSWORD)

    fun storePassword(password: String) = storeStringProperty(KEY_PASSWORD, password)

    fun loadProductList() = loadStringProperty(KEY_PRODUCT_LIST)?.let {
        gson.fromJson<List<ProductDetail>>(it, object : TypeToken<List<ProductDetail>>() {}.type)
    }

    fun storeProductList(productList: List<ProductDetail>) =
        storeStringProperty(KEY_PRODUCT_LIST, gson.toJson(productList))

    fun clearAll() = sharedPreferences.edit { clear() }
}
