package com.android.shoppinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnFinishedEditingListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    private var fragmentContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentContainer = findViewById(R.id.container_fragment)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = ShopListAdapter()
        setupRecyclerView()
        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }

        val floatingBtn = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        floatingBtn.setOnClickListener {
            if(isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAdd(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    override fun onFinishedEditingListener() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
    private fun isOnePaneMode(): Boolean {
        return fragmentContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, fragment)
            .addToBackStack(null)
            .commit()
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
            if(isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEdit(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupLongClick() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}