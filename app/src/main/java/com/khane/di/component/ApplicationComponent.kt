package com.khane.di.component

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import android.content.res.Resources
import com.khane.core.AppPreferences
import com.khane.core.Session
import com.khane.core.Validator
import com.khane.data.repository.UserRepository
import com.khane.di.App
import com.khane.di.module.ApplicationModule
import com.khane.di.module.NetModule
import com.khane.di.module.ServiceModule
import com.khane.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import java.io.File
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by hlink21 on 9/5/16.
 */
@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, NetModule::class, ServiceModule::class])
interface ApplicationComponent {

    fun context(): Context

    @Named("cache")
    fun provideCacheDir(): File

    fun provideResources(): Resources

    fun provideCurrentLocale(): Locale

    fun provideViewModelFactory(): ViewModelProvider.Factory

    fun inject(appShell: App)

    fun provideSession(): Session

    fun provideAppPreferences(): AppPreferences

    fun provideValidator(): Validator

    fun provideUserRepository(): UserRepository

    @Component.Builder
    interface ApplicationComponentBuilder {

        @BindsInstance
        fun apiKey(@Named("api-key") apiKey: String): ApplicationComponentBuilder

        @BindsInstance
        fun application(application: Application): ApplicationComponentBuilder

        fun build(): ApplicationComponent
    }

}
