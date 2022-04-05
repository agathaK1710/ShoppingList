package com.android.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.shoppinglist.domain.ShopItem
import com.android.shoppinglist.domain.ShopListRepository
import java.lang.Exception
import kotlin.random.Random

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    val mapper = ShopListMapper()
    val shopListDao = AppDatabase.getInstance(application).shopListDao()


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