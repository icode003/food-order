package com.khane.di.module

import com.khane.ui.base.BaseActivity
import com.khane.ui.manager.FragmentHandler
import com.khane.ui.manager.Navigator

import javax.inject.Named

import dagger.Module
import dagger.Provides

/**
 * Created by hlink21 on 9/5/16.
 */
@Module
class ActivityModule {

    @Provides
    @com.khane.di.PerActivity
    internal fun navigator(activity: BaseActivity): Navigator {
        return activity
    }

    @Provides
    @com.khane.di.PerActivity
    internal fun fragmentManager(baseActivity: BaseActivity): androidx.fragment.app.FragmentManager {
        return baseActivity.supportFragmentManager
    }

    @Provides
    @com.khane.di.PerActivity
    @Named("placeholder")
    internal fun placeHolder(baseActivity: BaseActivity): Int {
        return baseActivity.findFragmentPlaceHolder()
    }

    @Provides
    @com.khane.di.PerActivity
    internal fun fragmentHandler(fragmentManager: com.khane.ui.manager.FragmentManager): FragmentHandler {
        return fragmentManager
    }


}
