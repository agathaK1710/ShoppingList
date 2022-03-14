package com.android.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.shoppinglist.data.ShopListRepositoryImpl
import com.android.shoppinglist.domain.*
import java.lang.Exception

class ShopItemViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemForId(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(shopItemId: Int) {
        val shopItem = getShopItemUseCase.getShopItemForId(shopItemId)
        _shopItem.value = shopItem
    }

    fun addShopItem(nameInput: String?, countInput: String?) {
        val name = parseName(nameInput)
        val count = parseCount(countInput)
        if (validInput(name, count)) {
            val shopItem = ShopItem(count, name, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(nameInput: String?, countInput: String?) {
        val name = parseName(nameInput)
        val count = parseCount(countInput)
        if (validInput(name, count)) {
            val shopItem = _shopItem.value
            shopItem?.let {
                val item = it.copy(count = count, name = name)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }
        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}