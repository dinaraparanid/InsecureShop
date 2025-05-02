package com.insecureshop.feature.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.insecureshop.feature.product.ProductDetail
import com.insecureshop.databinding.CartItemBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val cartList = mutableListOf<ProductDetail>()

    class CartViewHolder(internal val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun loadCarts(list: List<ProductDetail>) {
        cartList.clear()
        cartList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context))
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val prodDetail = cartList[position]

        Glide.with(holder.binding.picture.context)
            .load(prodDetail.imageUrl)
            .into(holder.binding.picture)

        holder.binding.prodName.text = prodDetail.name
        holder.binding.prodPrice.text = "$ ${prodDetail.price}"
        holder.binding.productCount.text = " Qty : ${prodDetail.qty}"
    }
}