package com.android.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shopItem")
    fun getShopList(): LiveData<List<ShopItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItemDBModel: ShopItemDBModel)

    @Query("SELECT * FROM shopItem WHERE id=:shopItemId LIMIT 1")
    suspend fun getShopItem(shopItemId: Int): ShopItemDBModel

    @Query("DELETE FROM shopItem WHERE id = :shopItemId")
    suspend fun deleteShopItem(shopItemId: Int)
}