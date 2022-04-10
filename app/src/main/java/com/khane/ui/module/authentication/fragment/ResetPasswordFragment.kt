package com.khane.ui.module.authentication.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.khane.R
import com.khane.core.Validator
import com.khane.databinding.ResetPasswordFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.exception.ApplicationException
import com.khane.ui.base.BaseFragment
import com.khane.utils.getString
import javax.inject.Inject

class ResetPasswordFragment : BaseFragment<ResetPasswordFragmentBinding>() {

    @Inject
    lateinit var validator: Validator

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ResetPasswordFragmentBinding {
        return ResetPasswordFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        mBinding.buttonSend.setOnClickListener {
            if (isValidUserInput()) {
                showToast(getString(R.string.toast_reset_password_success))
                navigator.finish()
            }
        }
    }

    private fun isValidUserInput(): Boolean {
        return try {
            validator.submit(mBinding.textInputLayoutNewPassword.editText!!)
                .checkEmpty()
                .errorMessage(getString(R.string.validation_enter_new_password))
                .check()
            validator.submit(mBinding.textInputLayoutConfirmPassword.editText!!)
                .checkEmpty()
                .errorMessage(getString(R.string.validation_enter_confirm_password))
                .check()
            validator.match(
                mBinding.textInputLayoutNewPassword.editText!!.getString(),
                mBinding.textInputLayoutConfirmPassword.editText!!.getString(),
                getString(R.string.validation_password_mismatch)
            )
            true
        } catch (e: ApplicationException) {
            super.onError(e)
            false
        }
    }

}