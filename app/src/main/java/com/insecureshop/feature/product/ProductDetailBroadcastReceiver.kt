package com.insecureshop.feature.product

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.insecureshop.feature.webview.WebViewActivity

private const val REDIRECT_URL = "https://www.insecureshopapp.com/"

internal class ProductDetailBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.startActivity(
            Intent(WebViewActivity.INTENT_ACTION)
                .putExtra(WebViewActivity.EXTRA_URL, REDIRECT_URL)
        )
    }
}
