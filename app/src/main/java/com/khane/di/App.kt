package com.khane.di

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.khane.utils.LocaleHelper

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        Injector.INSTANCE.initAppComponent(this, "skkyn")
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleHelper.setLocale(this)
/*//        baseContext.applicationContext.createConfigurationContext(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Configuration.UI_MODE_NIGHT_YES ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }*/
    }
}
