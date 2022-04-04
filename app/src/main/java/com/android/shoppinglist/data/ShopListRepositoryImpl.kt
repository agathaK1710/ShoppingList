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
    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)})
    private var autoIncrementId = 0

    init {
        for (i in 0..1000){
            addShopItem(ShopItem(i, "name$i", Random.nextBoolean()))
        }
    }

    val mapper = ShopListMapper()
    val shopListDao = AppDatabase.getInstance(application).shopListDao()

    private fun updateList(){

        shopListLD.value = shopList.toList()
    }

    override fun getShopList(): LiveData<List<ShopItem>>  = Transformations.map(shopListDao.getShopList()){
        mapper.shopItemDBListToShopItemList(it)
    }

    override fun removeShopItem(item: ShopItem) {
        shopListDao.deleteShopItem(item.id)
    }

    override fun getShopItemForId(id: Int): ShopItem {
        return mapper.shopItemDBModelToShopItem(shopListDao.getShopItem(id))
    }

    override fun editShopItem(item: ShopItem) {
       shopListDao.addShopItem(mapper.shopItemToShopItemDBModel(item))
    }

    override fun addShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.shopItemToShopItemDBModel(item))
    }
}