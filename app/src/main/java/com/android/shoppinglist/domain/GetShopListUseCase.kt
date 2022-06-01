package com.android.shoppinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(private val listRepository: ShopListRepository) {

    fun getShopList(): LiveData<List<ShopItem>> {
        return listRepository.getShopList()
    }
}