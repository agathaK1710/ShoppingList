package com.android.shoppinglist.di

import androidx.lifecycle.ViewModel
import com.android.shoppinglist.presentation.MainViewModel
import com.android.shoppinglist.presentation.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun binMainViewModule(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun binShopItemViewModule(viewModel: ShopItemViewModel): ViewModel
}