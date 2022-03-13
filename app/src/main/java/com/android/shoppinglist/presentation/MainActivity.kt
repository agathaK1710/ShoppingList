package com.android.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.shoppinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = ShopListAdapter()
        setupRecyclerView()
        viewModel.shopList.observe(this) {
            adapter.shopList = it
        }
    }

    val itemTouchHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.removeShopItem(adapter.shopList[viewHolder.adapterPosition])
        }
    })


    fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_shop_list)
        recyclerView.adapter = adapter
        recyclerView.recycledViewPool.setMaxRecycledViews(R.layout.item_shop_enabled, 20)
        recyclerView.recycledViewPool.setMaxRecycledViews(R.layout.item_shop_disabled, 20)
        setupLongClick()
        setupClick()
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupClick() {
        adapter.onShopItemClickListener = {
            Log.d("mainActivity", it.toString())
        }
    }

    private fun setupLongClick() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}