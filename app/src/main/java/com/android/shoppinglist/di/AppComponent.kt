package com.android.shoppinglist.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ViewModelModule::class, DataModule::class])
interface AppComponent {

    @Component.Factory
    interface AppComponentFactory{
        fun create(
            @BindsInstance
            application:Application
        ): AppComponent
    }
}