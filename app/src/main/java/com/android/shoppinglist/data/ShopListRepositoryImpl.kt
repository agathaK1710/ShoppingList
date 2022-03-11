package com.android.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.shoppinglist.domain.ShopItem
import com.android.shoppinglist.domain.ShopListRepository
import java.lang.Exception

object ShopListRepositoryImpl : ShopListRepository {
    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    private fun updateList(){
        shopListLD.value = shopList.toList()
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun removeShopItem(item: ShopItem) {
        shopList.remove(item)
        updateList()
    }

    override fun getShopItemForId(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw Exception("Item is not found")
    }

    override fun editShopItem(item: ShopItem) {
        val oldElement = getShopItemForId(item.id)
        shopList.remove(oldElement)
        shopList.add(item)
    }

    override fun addShopItem(item: ShopItem) {
        if(item.id == ShopItem.UNDEFIND_ID) {
            item.id = autoIncrementId++
        }
        shopList.add(item)
        updateList()
    }
}