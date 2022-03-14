package com.android.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.shoppinglist.R
import com.android.shoppinglist.domain.ShopItem

class ShopListAdapter:
    ListAdapter<ShopItem, ShopViewHolder>(ShopItemDiffCallback()) {
    var count = 0
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        Log.d("ShopListAdapter", "onCreateViewHolder, count: ${++count}")
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return ShopViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) R.layout.item_shop_enabled
        else R.layout.item_shop_disabled
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val shopItem = getItem(position)
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
}