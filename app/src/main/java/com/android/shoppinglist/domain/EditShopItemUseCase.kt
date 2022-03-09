package com.android.shoppinglist.domain

class EditShopItemUseCase(private val listRepository: ShopListRepository) {
    fun editShopItem (shopItem: ShopItem){
        listRepository.editShopItem(shopItem)
    }
}