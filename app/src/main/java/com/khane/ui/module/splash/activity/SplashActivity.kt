package com.khane.ui.module.splash.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.khane.R
import com.khane.databinding.SplashActivityBinding
import com.khane.di.component.ActivityComponent
import com.khane.ui.base.BaseActivity
import com.khane.ui.module.authentication.activity.AuthenticationActivity
import com.khane.ui.module.main.activity.MainActivity
import com.khane.utils.Limits
import com.khane.utils.Utils
import com.khane.utils.ViewUtils

class SplashActivity : BaseActivity() {

    private lateinit var mBinding: SplashActivityBinding

    override fun findFragmentPlaceHolder(): Int = 0

    override fun findContentView(): Int = R.layout.splash_activity

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun bindViewWithViewBinding(view: View) {
        mBinding = SplashActivityBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewUtils.setTransparentStatusBar(this)
        Handler(Looper.getMainLooper()).postDelayed({
            loadActivity(AuthenticationActivity::class.java).start()
//            if (Utils.isLogin(appPreferences)) {
//                loadActivity(MainActivity::class.java).byFinishingAll().start()
//            } else {
//                loadActivity(AuthenticationActivity::class.java).start()
//            }
//            this.finish()
        }, Limits.SPLASH_TIME_OUT)
    }
}