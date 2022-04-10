package com.khane.di.component


import com.khane.di.module.ActivityModule
import com.khane.di.module.FragmentModule
import com.khane.ui.base.BaseActivity
import com.khane.ui.base.IsolatedActivity
import com.khane.ui.manager.Navigator
import com.khane.ui.module.authentication.activity.AuthenticationActivity
import com.khane.ui.module.main.activity.MainActivity
import com.khane.ui.module.splash.activity.SplashActivity

import dagger.BindsInstance
import dagger.Component

/**
 * Created by hlink21 on 9/5/16.
 */
@com.khane.di.PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun activity(): BaseActivity

    fun navigator(): Navigator

    operator fun plus(fragmentModule: FragmentModule): FragmentComponent

    fun inject(splashActivity: SplashActivity)
    fun inject(splashActivity: AuthenticationActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(isolatedActivity: IsolatedActivity)

    @Component.Builder
    interface Builder {

        fun bindApplicationComponent(applicationComponent: ApplicationComponent): Builder

        @BindsInstance
        fun bindActivity(baseActivity: BaseActivity): Builder

        fun build(): ActivityComponent
    }
}
