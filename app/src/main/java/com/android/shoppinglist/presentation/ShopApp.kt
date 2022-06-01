package com.android.shoppinglist.presentation

import android.app.Application
import com.android.shoppinglist.di.DaggerAppComponent

class ShopApp:Application() {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}