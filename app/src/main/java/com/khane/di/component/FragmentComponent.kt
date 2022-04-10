package com.khane.di.component


import com.khane.di.module.FragmentModule
import com.khane.ui.base.BaseFragment
import com.khane.ui.module.authentication.fragment.*
import com.khane.ui.module.main.auctionrooms.fragment.AuctionRoomsFragment
import com.khane.ui.module.main.cardealers.fragment.CarDealersFragment
import com.khane.ui.module.main.home.fragment.HomeFragment
import com.khane.ui.module.main.user.fragment.UserFragment
import dagger.Subcomponent

/**
 * Created by hlink21 on 31/5/16.
 */

@com.khane.di.PerFragment
@Subcomponent(modules = [(FragmentModule::class)])
interface FragmentComponent {
    fun baseFragment(): BaseFragment<*>
    fun inject(chooseLanguageFragment: ChooseLanguageFragment)
    fun inject(chooseLanguageFragment: SignUpLoginFragment)
    fun inject(signUpFragment: SignUpFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(verificationFragment: VerificationFragment)
    fun inject(forgetPasswordFragment: ForgetPasswordFragment)
    fun inject(resetPasswordFragment: ResetPasswordFragment)
    fun inject(termsAndConditionsFragment: TermsAndConditionsFragment)
    fun inject(privacyPolicyFragment: PrivacyPolicyFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(auctionRoomsFragment: AuctionRoomsFragment)
    fun inject(carDealersFragment: CarDealersFragment)
    fun inject(userFragment: UserFragment)

}
