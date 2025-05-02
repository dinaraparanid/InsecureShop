package com.insecureshop.feature.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.insecureshop.feature.product.ProductListActivity
import com.insecureshop.R
import com.insecureshop.databinding.ActivityLoginBinding
import com.insecureshop.util.Prefs
import com.insecureshop.util.Util
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var util: Util

    @Inject
    lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.btnLogin.setOnClickListener {
            onLogin()
        }
    }

    private fun onLogin() {
        val username = binding.edtUserName.text.toString()
        val password = binding.edtPassword.text.toString()

        if (util.verifyUserNamePassword(username, password)) {
            prefs.storeUsername(username)
            prefs.storePassword(password)
            util.storeProductList()

            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(
                this,
                getString(R.string.invalid_credentials),
                Toast.LENGTH_LONG,
            ).show()
        }
    }
}
