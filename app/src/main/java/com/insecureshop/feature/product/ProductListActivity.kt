package com.insecureshop.feature.product

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.insecureshop.R
import com.insecureshop.databinding.ActivityProductListBinding
import com.insecureshop.feature.about_us.AboutUsActivity
import com.insecureshop.feature.cart.CartListActivity
import com.insecureshop.feature.login.LoginActivity
import com.insecureshop.util.Prefs
import com.insecureshop.util.Util
import com.insecureshop.util.ext.registerReceiverCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val PRODUCT_DETAIL_RECEIVER_INTENT = "com.insecureshop.action.PRODUCT_DETAIL"

@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {
    private val productDetailBroadcastReceiver = ProductDetailBroadcastReceiver()

    @Inject
    lateinit var util: Util

    @Inject
    lateinit var prefs: Prefs

    private lateinit var binding: ActivityProductListBinding

    private val adapter by lazy {
        ProductAdapter(util)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val username = prefs.loadUsername()
        val password = prefs.loadPassword()

        if (username == null || password == null) {
            logOut()
            return
        }

        val isLoggedIn = util.verifyUserNamePassword(
            username = username,
            password = password,
        )

        if (isLoggedIn.not()) {
            logOut()
            return
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        setSupportActionBar(binding.toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
            view.updatePadding(0, systemInsets.top, 0, systemInsets.bottom)
            binding.recyclerview.updatePadding(systemInsets.left, 0, systemInsets.right, 0)
            WindowInsetsCompat.CONSUMED
        }

        val intentFilter = IntentFilter(PRODUCT_DETAIL_RECEIVER_INTENT)
        registerReceiverCompat(productDetailBroadcastReceiver, intentFilter)

        binding.recyclerview.layoutManager = GridLayoutManager(applicationContext, 2)
        binding.recyclerview.adapter = adapter.apply {
            updateProductList(util.loadProductList())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        return when (item.itemId) {
            R.id.logout -> {
                logOut()
                true
            }

            R.id.cart -> {
                navigateToActivity<CartListActivity>()
                true
            }

            R.id.about -> {
                navigateToActivity<AboutUsActivity>()
                true
            }

            else -> false
        }
    }

    private fun logOut() {
        prefs.clearAll()
        navigateToActivity<LoginActivity>()
        finish()
    }

    private inline fun <reified T : Activity> navigateToActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}
