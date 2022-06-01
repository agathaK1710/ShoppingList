package com.android.shoppinglist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.shoppinglist.R
import com.android.shoppinglist.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnFinishedEditingListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapterShop: ShopListAdapter
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val component by lazy {
        (application as ShopApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        adapterShop = ShopListAdapter()
        setupRecyclerView()
        viewModel.shopList.observe(this) {
            adapterShop.submitList(it)
        }
        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePaneMode()) {
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
        return binding.containerFragment == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }


    private val itemTouchHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.removeShopItem(adapterShop.currentList[viewHolder.adapterPosition])
        }
    })


    private fun setupRecyclerView() {
        with(binding.rvShopList) {
            adapter = adapterShop
            recycledViewPool.setMaxRecycledViews(R.layout.item_shop_enabled, 20)
            recycledViewPool.setMaxRecycledViews(R.layout.item_shop_disabled, 20)
            setupLongClick()
            setupClick()
            itemTouchHelper.attachToRecyclerView(this)
        }
    }


private fun setupClick() {
    adapterShop.onShopItemClickListener = {
        if (isOnePaneMode()) {
            val intent = ShopItemActivity.newIntentEdit(this, it.id)
            startActivity(intent)
        } else {
            launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
        }
    }
}

private fun setupLongClick() {
    adapterShop.onShopItemLongClickListener = {
        viewModel.changeEnableState(it)
    }
}
}