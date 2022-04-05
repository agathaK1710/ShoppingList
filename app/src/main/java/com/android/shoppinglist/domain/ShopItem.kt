package com.android.shoppinglist.domain

data class ShopItem(
    val count: Int,
    val name: String,
    val enabled: Boolean,
    var id: Int = UNDEFIND_ID
){
    companion object {
        val UNDEFIND_ID = 0
    }
}
