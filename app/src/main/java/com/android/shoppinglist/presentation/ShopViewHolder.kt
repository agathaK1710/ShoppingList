package com.android.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.shoppinglist.R

class ShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewName = view.findViewById<TextView>(R.id.tv_name)
    val textViewCount = view.findViewById<TextView>(R.id.tv_count)
}