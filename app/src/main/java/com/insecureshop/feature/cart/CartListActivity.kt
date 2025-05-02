package com.insecureshop.feature.cart

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.insecureshop.R
import com.insecureshop.databinding.ActivityCartListBinding
import com.insecureshop.util.Util
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartListBinding

    @Inject
    lateinit var util: Util

    private val adapter by lazy { CartAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart_list)
        setSupportActionBar(binding.toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
            view.updatePadding(0, systemInsets.top, 0, systemInsets.bottom)
            binding.recyclerview.updatePadding(systemInsets.left, 0, systemInsets.right, 0)
            WindowInsetsCompat.CONSUMED
        }

        title = "Cart"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerview.adapter = adapter.apply {
            loadCarts(util.getCartProduct())
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
