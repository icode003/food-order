package com.khane.ui.module.authentication.activity

import android.os.Bundle
import android.view.View
import com.khane.R
import com.khane.databinding.AuthenticationActivityBinding
import com.khane.di.component.ActivityComponent
import com.khane.ui.base.BaseActivity
import com.khane.ui.module.authentication.fragment.ChooseLanguageFragment
import com.khane.ui.module.authentication.fragment.SignUpLoginFragment

class AuthenticationActivity : BaseActivity() {

    private lateinit var mBinding: AuthenticationActivityBinding

    override fun findFragmentPlaceHolder(): Int = R.id.placeHolder

    override fun findContentView(): Int = R.layout.authentication_activity

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun bindViewWithViewBinding(view: View) {
        mBinding = AuthenticationActivityBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        load(SignUpLoginFragment::class.java).replace(false)
    }
}