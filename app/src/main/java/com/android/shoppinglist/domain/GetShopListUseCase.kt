package com.android.shoppinglist.domain

class GetShopListUseCase(private val listRepository: ShopListRepository) {

    fun getShopList(): List<ShopItem>{
        return listRepository.getShopList()
    }
}