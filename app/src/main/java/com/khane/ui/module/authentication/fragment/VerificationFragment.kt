package com.khane.ui.module.authentication.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.khane.R
import com.khane.core.Validator
import com.khane.databinding.VerificationFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.exception.ApplicationException
import com.khane.ui.base.BaseFragment
import com.khane.ui.module.main.activity.MainActivity
import com.khane.utils.StringUtils
import com.khane.utils.Utils
import javax.inject.Inject

class VerificationFragment : BaseFragment<VerificationFragmentBinding>() {

    @Inject
    lateinit var validator: Validator

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): VerificationFragmentBinding {
        return VerificationFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        mBinding.pinView.setAnimationEnable(true)
        mBinding.imageViewRight.setOnClickListener { onClickRight() }
    }

    private fun isValidUserInput(): Boolean {
        return try {
            validator.submit(mBinding.pinView)
                .checkEmpty()
                .errorMessage(getString(R.string.validation_enter_verification_code))
                .checkMinDigits(4)
                .errorMessage(getString(R.string.validation_enter_valid_verification_code))
                .check()
            true
        } catch (e: ApplicationException) {
            super.onError(e)
            false
        }
    }

    private fun onClickRight() {
        if (isValidUserInput()) {

            Utils.setLogin(appPreferences, true)
            navigator.loadActivity(MainActivity::class.java).byFinishingAll().start()

//            if (StringUtils.getSourceScreen(arguments) == SignUpFragment::class.java.simpleName) {
//                hideKeyBoard()
//                Utils.setLogin(appPreferences, true)
//                navigator.loadActivity(MainActivity::class.java).byFinishingAll().start()
//            } else if (StringUtils.getSourceScreen(arguments) == ForgetPasswordFragment::class.java.simpleName) {
//                hideKeyBoard()
//                navigator.load(ResetPasswordFragment::class.java).replace(true)
//            }
        }
    }

}