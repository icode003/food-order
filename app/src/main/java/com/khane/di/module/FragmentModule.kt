package com.khane.di.module

import com.khane.ui.base.BaseFragment
import dagger.Module
import dagger.Provides

/**
 * Created by hlink21 on 31/5/16.
 */
@Module
class FragmentModule(private val baseFragment: BaseFragment<*>) {

    @Provides
    @com.khane.di.PerFragment
    internal fun provideBaseFragment(): BaseFragment<*> {
        return baseFragment
    }

}
