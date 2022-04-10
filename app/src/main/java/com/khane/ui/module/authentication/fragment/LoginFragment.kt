package com.khane.ui.module.authentication.fragment

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khane.R
import com.khane.core.Validator
import com.khane.data.pojo.Country
import com.khane.databinding.LoginFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.exception.ApplicationException
import com.khane.ui.base.BaseFragment
import com.khane.ui.base.CountryCodeDialogFragment
import com.khane.ui.base.IsolatedActivity
import com.khane.ui.module.main.activity.MainActivity
import com.khane.utils.*
import javax.inject.Inject

class LoginFragment : BaseFragment<LoginFragmentBinding>() {

    @Inject
    lateinit var validator: Validator

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): LoginFragmentBinding {
        return LoginFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        setTitle()
        mBinding.textInputLayoutCountryCode.editText?.setCountryCode()
        setTextWatcherForEmailPhone()
        setClickListener()
    }

    private fun setTitle() {
        (parentFragment as SignUpLoginFragment).setTitle(getString(R.string.label_login))
    }

    private fun setTextWatcherForEmailPhone() {
        mBinding.textInputLayoutEmailMobile.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.isEmpty()) {
                    mBinding.textInputLayoutEmailMobile.hint =
                        getString(R.string.hint_email_or_mobile_number)
                    mBinding.textInputLayoutCountryCode.visibility = View.GONE
                    return
                }
                if (StringUtils.isDigit("$editable")) {
                    mBinding.textInputLayoutEmailMobile.editText?.filters =
                        arrayOf<InputFilter>(InputFilter.LengthFilter(resources.getInteger(R.integer.phone_max_length)))
                    mBinding.textInputLayoutCountryCode.visibility = View.VISIBLE
                    mBinding.textInputLayoutEmailMobile.hint =
                        getString(R.string.hint_mobile_number)
                } else {
                    mBinding.textInputLayoutEmailMobile.editText?.filters =
                        arrayOf<InputFilter>(InputFilter.LengthFilter(resources.getInteger(R.integer.email_max_length)))
                    mBinding.textInputLayoutCountryCode.visibility = View.GONE
                    mBinding.textInputLayoutEmailMobile.hint = getString(R.string.hint_email)
                }
            }
        })
    }

    private fun setClickListener() {
        mBinding.textInputLayoutCountryCode.editText?.setOnClickListener { onViewClick(it) }
        mBinding.textViewForgetPassword.setOnClickListener { onViewClick(it) }
        mBinding.buttonCreate.setOnClickListener { onViewClick(it) }
    }

    override fun onViewClick(view: View) {
        when (view) {
            mBinding.textInputLayoutCountryCode.editText -> onClickCountryCode()
//            mBinding.textViewForgetPassword -> {
//                navigator.loadActivity(
//                    IsolatedActivity::class.java,
//                    ForgetPasswordFragment::class.java
//                ).start()
//            }
            mBinding.buttonCreate -> {

                hideKeyBoard()
                if (isValidUserInput()) {
                    navigator.load(VerificationFragment::class.java)
                        .setBundle(StringUtils.getSourceScreenBundle(javaClass.simpleName)).replace(true)
//                    Utils.setLogin(appPreferences, true)
//                    navigator.loadActivity(MainActivity::class.java).byFinishingAll().start()
                }
            }
        }
    }

    private fun onClickCountryCode() {
        val countryCodeDialogFragment =
            CountryCodeDialogFragment(onEventListener = { country: Country ->
                mBinding.textInputLayoutCountryCode.editText?.setText(country.phoneCode)
            })
        countryCodeDialogFragment.show(childFragmentManager, countryCodeDialogFragment.tag)
    }

    private fun isValidUserInput(): Boolean {
        return try {
            validator.submit(mBinding.textInputLayoutEmailMobile.editText!!)
                .checkEmpty()
                .errorMessage(getString(R.string.validation_enter_email_or_mobile))
                .check()

            if (StringUtils.isDigit(mBinding.textInputLayoutEmailMobile.editText.getString())) {
                validator.submit(mBinding.textInputLayoutEmailMobile.editText!!)
                    .checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_mobile_number))
                    .checkMinDigits(8)
                    .errorMessage(getString(R.string.validation_valid_mobile_number))
                    .checkMaxDigits(11)
                    .errorMessage(getString(R.string.validation_valid_mobile_number))
                    .check()
            } else {
                validator.submit(mBinding.textInputLayoutEmailMobile.editText!!)
                    .checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_email))
                    .checkValidEmail()
                    .errorMessage(getString(R.string.validation_enter_valid_email))
                    .check()
            }
//            validator.submit(mBinding.textInputLayoutPassword.editText!!)
//                .checkEmpty()
//                .errorMessage(getString(R.string.validation_enter_password))
//                .check()
            true
        } catch (e: ApplicationException) {
            super.onError(e)
            false
        }
    }

}