package com.khane.ui.module.main.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.khane.R
import com.khane.databinding.MainActivityBinding
import com.khane.di.component.ActivityComponent
import com.khane.ui.base.BaseActivity
import com.khane.ui.module.main.auctionrooms.fragment.AuctionRoomsFragment
import com.khane.ui.module.main.cardealers.fragment.CarDealersFragment
import com.khane.ui.module.main.home.fragment.HomeFragment
import com.khane.ui.module.main.user.fragment.UserFragment


class MainActivity : BaseActivity() {

    private lateinit var mBinding: MainActivityBinding

    override fun findFragmentPlaceHolder(): Int = 0

    override fun findContentView(): Int = R.layout.main_activity

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun bindViewWithViewBinding(view: View) {
        mBinding = MainActivityBinding.bind(view)
    }

    /* override fun onStart() {
         super.onStart()
         val navController: NavController =
             Navigation.findNavController(this, R.id.navHostFragment)
         NavigationUI.setupWithNavController(mBinding.bottomNavigationView, navController)
     }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadFragment(HomeFragment())
        setBottomNavigationItemSelector()
    }

    private fun setBottomNavigationItemSelector() {
        mBinding.includeNavigationMain.bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.bottomNavigationHome -> {
                    loadFragment(HomeFragment())
                }
                R.id.bottomNavigationAuctionRooms -> {
                    loadFragment(AuctionRoomsFragment())
                }
                R.id.bottomNavigationCarDealers -> {
                    loadFragment(CarDealersFragment())
                }
                R.id.bottomNavigationUser -> {
                    loadFragment(UserFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayoutContainer, fragment)
            .commit()
    }
}