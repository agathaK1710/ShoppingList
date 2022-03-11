package com.android.shoppinglist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.shoppinglist.R
import com.android.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayout = findViewById(R.id.linearL)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            showShopList(it)
        }
    }

    fun showShopList(list: List<ShopItem>) {
        linearLayout.removeAllViews()
        for (shopItem in list) {
            val itemId =
                if (shopItem.enabled) R.layout.item_shop_enabled else R.layout.item_shop_disabled
            val view = LayoutInflater.from(this).inflate(itemId, linearLayout, false)
            val textViewName = view.findViewById<TextView>(R.id.tv_name)
            val textViewCount = view.findViewById<TextView>(R.id.tv_count)
            textViewName.text = shopItem.name
            textViewCount.text = shopItem.count.toString()
            linearLayout.addView(view)

            view.setOnLongClickListener {
                viewModel.changeEnableState(shopItem)
                true
            }
        }
    }

}