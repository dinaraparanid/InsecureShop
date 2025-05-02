package com.insecureshop.feature.about_us

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.insecureshop.R
import com.insecureshop.broadcast_receiver.WebUrlReceiver
import com.insecureshop.databinding.ActivityAboutUsBinding
import com.insecureshop.util.ext.registerReceiverCompat
import dagger.hilt.android.AndroidEntryPoint

// Inlined (no static field) when decompiled to Java with JADX
private const val RECEIVER_ACTION = "com.insecureshop.CUSTOM_INTENT"

@AndroidEntryPoint
class AboutUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutUsBinding

    private val receiver by lazy {
        WebUrlReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us)

        binding.sendDataToBroadcast.setOnClickListener {
            showInfo()
        }

        registerReceiverCompat(receiver, IntentFilter(RECEIVER_ACTION))
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun showInfo() {
        binding.textView.text = getString(R.string.about_us_description)
    }
}