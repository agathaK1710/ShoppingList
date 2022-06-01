package com.android.shoppinglist.di

import android.app.Application
import com.android.shoppinglist.presentation.MainActivity
import com.android.shoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ViewModelModule::class, DataModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface AppComponentFactory{
        fun create(
            @BindsInstance
            application:Application
        ): AppComponent
    }
}