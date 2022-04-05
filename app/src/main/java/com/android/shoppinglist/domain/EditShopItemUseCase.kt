package com.android.shoppinglist.domain

class EditShopItemUseCase(private val listRepository: ShopListRepository) {
    suspend fun editShopItem (shopItem: ShopItem){
        listRepository.editShopItem(shopItem)
    }
}