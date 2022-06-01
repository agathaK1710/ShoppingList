package com.android.shoppinglist.data

import com.android.shoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {

    fun shopItemToShopItemDBModel(shopItem: ShopItem) = ShopItemDBModel(
        id = shopItem.id,
        count = shopItem.count,
        name = shopItem.name,
        enabled = shopItem.enabled
    )

    fun shopItemDBModelToShopItem(shopItemDBModel: ShopItemDBModel) = ShopItem(
        id = shopItemDBModel.id,
        count = shopItemDBModel.count,
        name = shopItemDBModel.name,
        enabled = shopItemDBModel.enabled
    )

    fun shopItemDBListToShopItemList(shopItemDBList: List<ShopItemDBModel>) =
        shopItemDBList.map {
            shopItemDBModelToShopItem(it)
        }
}