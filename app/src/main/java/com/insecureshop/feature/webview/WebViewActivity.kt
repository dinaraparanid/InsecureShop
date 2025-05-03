package com.insecureshop.feature.webview

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import com.insecureshop.R
import com.insecureshop.databinding.ActivityWebviewBinding
import dagger.hilt.android.AndroidEntryPoint

// Inlined (no static field) when decompiled to Java with JADX
private const val USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Mobile Safari/537.36"

@AndroidEntryPoint
class WebViewActivity : AppCompatActivity() {

    companion object {
        const val INTENT_ACTION = "com.insecureshop.action.WEBVIEW"
        const val EXTRA_URL = "url"
    }

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview)
        setSupportActionBar(binding.toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
            view.updatePadding(0, systemInsets.top, 0, systemInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        title = getString(R.string.webview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.action != INTENT_ACTION) {
            return
        }

        val url = intent.getStringExtra(PrivateActivity.EXTRA_URL)

        with(binding.webview) {
            settings.javaScriptEnabled = false
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.userAgentString = USER_AGENT

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?,
                ): Boolean = request?.url?.host != DEFAULT_HOST
            }
        }

        if (url != null) {
            binding.webview.loadUrl(url)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // handle back arrow click
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
