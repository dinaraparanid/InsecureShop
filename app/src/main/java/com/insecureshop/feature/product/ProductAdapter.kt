package com.insecureshop.feature.product

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.insecureshop.R
import com.insecureshop.databinding.ProductItemBinding
import com.insecureshop.feature.webview.WebViewActivity
import com.insecureshop.util.Util

class ProductAdapter(
    private val util: Util,
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productList: MutableList<ProductDetail> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateProductList(list: List<ProductDetail>) {
        setProductList(list)
        notifyDataSetChanged()
    }

    private fun setProductList(list: List<ProductDetail>) {
        productList.clear()
        productList.addAll(list)
    }

    class ProductViewHolder(internal val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context))
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val prodDetail = productList[position]
        val context = holder.binding.root.context

        Glide.with(holder.binding.picture.context)
            .load(prodDetail.imageUrl)
            .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
            .into(holder.binding.picture)

        holder.binding.prodName.text = prodDetail.name
        holder.binding.productCount.text = prodDetail.qty.toString()
        holder.binding.prodPrice.text = "$ ${prodDetail.price}"

        holder.binding.icAdd.setOnClickListener {
            setProductList(util.updateProductItem(prodDetail.copy(qty = prodDetail.qty + 1)))
            notifyItemChanged(position)
        }

        holder.binding.icRemove.setOnClickListener {
            if (prodDetail.qty > 0) {
                setProductList(util.updateProductItem(prodDetail.copy(qty = prodDetail.qty - 1)))
                notifyItemChanged(position)
            }
        }

        holder.binding.moreInfo.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
                .setAction(WebViewActivity.INTENT_ACTION)
                .putExtra(WebViewActivity.EXTRA_URL, prodDetail.url)

            context.startActivity(intent)
        }
    }
}
