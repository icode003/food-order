package com.khane.ui.base

import android.os.Bundle
import android.view.View
import com.khane.R
import com.khane.databinding.IsolatedActivityBinding
import com.khane.di.component.ActivityComponent
import com.khane.ui.manager.ActivityStarter

class IsolatedActivity : BaseActivity() {

    private lateinit var mBinding: IsolatedActivityBinding

    override fun findFragmentPlaceHolder(): Int = R.id.placeHolder

    override fun findContentView(): Int = R.layout.isolated_activity

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun bindViewWithViewBinding(view: View) {
        mBinding = IsolatedActivityBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ViewUtils.setWhiteStatusBarWithBlackIcon(this)
        if (savedInstanceState == null) {
            val page = intent.getSerializableExtra(ActivityStarter.ACTIVITY_FIRST_PAGE)
            if (page != null) {
                intent.extras?.let {
                    load(page as Class<BaseFragment<*>>)
                        .setBundle(it)
                        .add(false)
                }
            }
        }
    }

}