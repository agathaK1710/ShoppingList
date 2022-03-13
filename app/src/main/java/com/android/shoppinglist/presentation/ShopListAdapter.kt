package com.android.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.shoppinglist.R
import com.android.shoppinglist.domain.ShopItem

class ShopListAdapter() : RecyclerView.Adapter<ShopListAdapter.ViewHolder>() {
    var shopList: List<ShopItem> = listOf()
    set(value) {
       val diff = DiffUtil.calculateDiff(ShopListDiffCallback(shopList, value))
        diff.dispatchUpdatesTo(this)
        field = value

    }
    var count = 0
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName = view.findViewById<TextView>(R.id.tv_name)
        val textViewCount = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("ShopListAdapter", "onCreateViewHolder, count: ${++count}")
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList[position].enabled) R.layout.item_shop_enabled
        else R.layout.item_shop_disabled
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopItem = shopList[position]
        with(holder) {
            textViewName.text = shopItem.name
            textViewCount.text = shopItem.count.toString()
            itemView.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }
            itemView.setOnClickListener {
                onShopItemClickListener?.invoke(shopItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }
}