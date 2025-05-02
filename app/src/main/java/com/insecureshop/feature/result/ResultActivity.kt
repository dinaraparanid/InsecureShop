package com.insecureshop.feature.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.insecureshop.content_provider.InsecureShopProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (intent?.data?.authority) {
            InsecureShopProvider.URI_AUTHORITY -> setResult(RESULT_CANCELED)
            else -> setResult(RESULT_OK, intent)
        }

        finish()
    }
}
