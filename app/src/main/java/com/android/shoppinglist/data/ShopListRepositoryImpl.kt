package com.android.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.android.shoppinglist.domain.ShopItem
import com.android.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val mapper: ShopListMapper,
    private val shopListDao: ShopListDao
) : ShopListRepository {

    override fun getShopList(): LiveData<List<ShopItem>>  = Transformations.map(shopListDao.getShopList()){
        mapper.shopItemDBListToShopItemList(it)
    }

    override suspend fun removeShopItem(item: ShopItem) {
        shopListDao.deleteShopItem(item.id)
    }

    override suspend fun getShopItemForId(id: Int): ShopItem {
        return mapper.shopItemDBModelToShopItem(shopListDao.getShopItem(id))
    }

    override suspend fun editShopItem(item: ShopItem) {
       shopListDao.addShopItem(mapper.shopItemToShopItemDBModel(item))
    }

    override suspend fun addShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.shopItemToShopItemDBModel(item))
    }
}