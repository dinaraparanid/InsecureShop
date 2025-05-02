package com.insecureshop.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.insecureshop.feature.webview.WebViewActivity

class WebUrlReceiver : BroadcastReceiver() {
    companion object {
        const val EXTRA_WEB_URL = "web_url"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val stringExtra = intent?.extras?.getString(EXTRA_WEB_URL)

        if (stringExtra.isNullOrBlank().not()) {
            context?.startActivity(
                Intent(context, WebViewActivity::class.java)
                    .putExtra(WebViewActivity.EXTRA_URL, stringExtra)
            )
        }
    }
}
