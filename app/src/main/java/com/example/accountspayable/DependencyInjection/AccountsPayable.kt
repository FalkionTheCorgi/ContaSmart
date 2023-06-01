package com.example.accountspayable.DependencyInjection

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AccountsPayable: Application(){


    override fun onCreate() {
        super.onCreate()


        startKoin {

            androidContext(this@AccountsPayable)
            modules(appModule)

        }
    }

}