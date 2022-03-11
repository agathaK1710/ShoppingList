package com.android.shoppinglist.domain

import androidx.lifecycle.LiveData

class GetShopListUseCase(private val listRepository: ShopListRepository) {

    fun getShopList(): LiveData<List<ShopItem>> {
        return listRepository.getShopList()
    }
}