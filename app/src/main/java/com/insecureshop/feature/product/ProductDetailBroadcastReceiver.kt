package com.insecureshop.feature.product

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.insecureshop.feature.webview.DEFAULT_HOST
import com.insecureshop.feature.webview.WebViewActivity

internal class ProductDetailBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.startActivity(
            Intent(context, WebViewActivity::class.java)
                .setAction(WebViewActivity.INTENT_ACTION)
                .putExtra(WebViewActivity.EXTRA_URL, DEFAULT_HOST)
        )
    }
}
