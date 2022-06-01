package com.android.shoppinglist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(private val listRepository: ShopListRepository) {
    suspend fun editShopItem (shopItem: ShopItem){
        listRepository.editShopItem(shopItem)
    }
}