package com.android.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
            adapter.submitList(it)
        }

        val floatingBtn = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        floatingBtn.setOnClickListener {
            val intent = ShopItemActivity.newIntentAdd(this)
            startActivity(intent)
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.removeShopItem(adapter.currentList[viewHolder.adapterPosition])
        }
    })


    private fun setupRecyclerView() {
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
            val intent = ShopItemActivity.newIntentEdit(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClick() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}