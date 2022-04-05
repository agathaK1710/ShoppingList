package com.android.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.shoppinglist.data.ShopListRepositoryImpl
import com.android.shoppinglist.domain.EditShopItemUseCase
import com.android.shoppinglist.domain.GetShopListUseCase
import com.android.shoppinglist.domain.RemoveShopItemUseCase
import com.android.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem){
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem){
        viewModelScope.launch {
            val item = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(item)
        }
    }
}