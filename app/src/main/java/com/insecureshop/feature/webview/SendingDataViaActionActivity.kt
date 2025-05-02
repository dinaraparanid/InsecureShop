package com.insecureshop.feature.webview

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.insecureshop.R
import com.insecureshop.databinding.ActivityImplicitIntentForNonExportedBinding
import dagger.hilt.android.AndroidEntryPoint

// Inlined (no static field) when decompiled to Java with JADX
private const val REDIRECT_URL = "https://www.insecureshop.com/"

@AndroidEntryPoint
class SendingDataViaActionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImplicitIntentForNonExportedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_implicit_intent_for_non_exported)
        setSupportActionBar(binding.toolbar)

        binding.logout.setOnClickListener { onSendData() }
    }

    private fun onSendData() {
        val intent = Intent(WebViewActivity.INTENT_ACTION)
        intent.putExtra(WebViewActivity.EXTRA_URL, REDIRECT_URL)

        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
        }
    }
}
