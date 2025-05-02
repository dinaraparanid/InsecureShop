package com.insecureshop.feature.webview

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.insecureshop.R
import com.insecureshop.databinding.ActivityPrivateBinding
import com.insecureshop.util.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// Inlined (no static field) when decompiled to Java with JADX
private const val USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Mobile Safari/537.36"
private const val VALID_URL = "https://www.insecureshopapp.com"

@AndroidEntryPoint
class PrivateActivity : AppCompatActivity() {

    companion object {
        const val INTENT_ACTION = "com.insecureshop.action.PRODUCT_DETAIL"
        const val EXTRA_URL = "url"
    }

    private lateinit var binding: ActivityPrivateBinding

    @Inject
    lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_private)
        setSupportActionBar(binding.toolbar)
        title = getString(R.string.webview)

        if (intent.action != INTENT_ACTION) {
            return
        }

        val url = intent.getStringExtra(EXTRA_URL) ?: VALID_URL

        with(binding.webview) {
            settings.javaScriptEnabled = false
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.userAgentString = USER_AGENT

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?,
                ): Boolean = request?.url?.toString() != VALID_URL
            }

            loadUrl(url)
        }

        prefs.storeUrl(url)
    }
}
