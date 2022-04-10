package com.khane.ui.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.*
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.ViewModelProvider
import com.khane.R
import com.khane.core.AppPreferences
import com.khane.core.Session
import com.khane.di.HasComponent
import com.khane.di.Injector
import com.khane.di.component.ActivityComponent
import com.khane.di.component.DaggerActivityComponent
import com.khane.exception.ApplicationException
import com.khane.exception.ServerException
import com.khane.ui.imagepicker.ImageVideoPickerDialog
import com.khane.ui.manager.*
import com.khane.utils.*
import com.throdle.exception.AuthenticationException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasComponent<ActivityComponent>, HasToolbar,
    Navigator {
    override val component: ActivityComponent
        get() = activityComponent

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigationFactory: FragmentNavigationFactory

    @Inject
    lateinit var activityStarter: ActivityStarter

    @Inject
    lateinit var appPreferences: AppPreferences

    private var mLoadingDialog: LoadingDialog? = null

    private lateinit var activityComponent: ActivityComponent


    override fun onCreate(savedInstanceState: Bundle?) {

        activityComponent = DaggerActivityComponent.builder()
            .bindApplicationComponent(Injector.INSTANCE.applicationComponent)
            .bindActivity(this)
            .build()

        inject(activityComponent)

        setContentView(findContentView())
        bindViewWithViewBinding((findViewById<ViewGroup>(android.R.id.content)).getChildAt(0))
        super.onCreate(savedInstanceState)
    }

    fun <F : BaseFragment<*>> getCurrentFragment(): F? {
        return if (findFragmentPlaceHolder() == 0) null else supportFragmentManager.findFragmentById(
            findFragmentPlaceHolder()
        ) as F
    }

    abstract fun findFragmentPlaceHolder(): Int

    @LayoutRes
    abstract fun findContentView(): Int

    abstract fun inject(activityComponent: ActivityComponent)

    abstract fun bindViewWithViewBinding(view: View)

    fun toggleLoader(show: Boolean) {
        if (show) {
            showLoader()
        } else {
            hideLoader()
        }
    }

    fun showLoader() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(this)
        }
        mLoadingDialog?.show()
    }

    fun hideLoader() {
        mLoadingDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    protected fun shouldGoBack(): Boolean {
        return true
    }

    override fun onBackPressed() {
        hideKeyboard()


        val currentFragment = getCurrentFragment<BaseFragment<*>>()
        if (currentFragment == null)
            super.onBackPressed()
        else if (currentFragment.onBackActionPerform() && shouldGoBack())
            super.onBackPressed()

        // pending animation
        // overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);

    }

    fun hideKeyboard() {
        // Check if no view has focus:

        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    override fun showToolbar(b: Boolean) {
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {

            if (b)
                supportActionBar.show()
            else
                supportActionBar.hide()
        }
    }

    override fun setToolbarTitle(title: CharSequence) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    override fun setToolbarTitle(@StringRes title: Int) {

        if (supportActionBar != null) {
            supportActionBar!!.setTitle(title)
            //appToolbarTitle.setText(name);
        }
    }

    override fun showBackButton(b: Boolean) {

        val supportActionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(b)
    }

    override fun setToolbarColor(@ColorRes color: Int) {

        /*if (toolbar != null) {
            toolbar.setBackgroundResource(color)
        }*/

    }


    override fun setToolbarElevation(isVisible: Boolean) {

        if (supportActionBar != null) {
            supportActionBar!!.elevation = if (isVisible) 8f else 0f
        }
    }

    fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }


    override fun <T : BaseFragment<*>> load(tClass: Class<T>): FragmentActionPerformer<T> {
        return navigationFactory.make(tClass)
    }

    override fun loadActivity(aClass: Class<out BaseActivity>): ActivityBuilder {
        return activityStarter.make(aClass)
    }

    override fun <T : BaseFragment<*>> loadActivity(
        aClass: Class<out BaseActivity>,
        pageTClass: Class<T>
    ): ActivityBuilder {
        return activityStarter.make(aClass).setPage(pageTClass)
    }


    override fun goBack() {
        onBackPressed()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase))
    }

    interface SnackBarListener {
        fun onDismiss()
    }

    fun showToast(message: String) {
        Utils.showToast(this, message)
    }

    override fun showErrorMessage(message: String, snackBarListener: SnackBarListener?) {
        /*if (getCurrentFragment<BaseFragment<*>>() != null) {
            hideKeyboard()
            *//*val make = ErrorMessageUtils.getErrorSnackbarDefault(
                this,
                getCurrentFragment<BaseFragment<*>>()?.view!!,
                message,
                snackBarListener
            )
            make.show()*//*
            ErrorMessageUtils.getCustomToast(this, message)
        }*/
        hideKeyboard()
        /*val make = ErrorMessageUtils.getErrorSnackbarDefault(
            this,
            getCurrentFragment<BaseFragment<*>>()?.view!!,
            message,
            snackBarListener
        )
        make.show()*/
        ErrorMessageUtils.getCustomToast(this, message)
    }

    override fun openImageOrVideoPickDefault(
        isCrop: Boolean,
        isCircularCrop: Boolean,
        isVideo: Boolean,
        imageVideoPickerListener: ImageVideoPickerDialog.ImageVideoPickerListener
    ) {
        Utils.showImageVideoPickerDialog(
            isCrop,
            isCircularCrop,
            isVideo,
            imageVideoPickerListener,
            supportFragmentManager
        )
    }

    fun onError(throwable: Throwable) {
        hideKeyboard()
        try {
            when (throwable) {
                is ServerException -> showErrorMessage(throwable.message.toString(), null)
                is ConnectException -> showErrorMessage(
                    getString(R.string.connection_exception),
                    null
                )
                is AuthenticationException -> {

                    //Logout
                    appPreferences.clearAll()
//                    appPreferences.putBoolean(Common.IS_FIRST_TIME, true)
/*
                    loadActivity(AuthenticationActivity::class.java)
                        .byFinishingAll().start()
*/

                }
                is ApplicationException -> {
                    showErrorMessage(throwable.errorMessage, null)
                }
                is SocketTimeoutException -> showErrorMessage(
                    getString(R.string.socket_time_out_exception),
                    null
                )
                else ->
//                    showMessage(throwable.message.toString())
                    showErrorMessage(getString(R.string.other_exception), null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setTextAppearance(textView: TextView, @StyleRes id: Int) {
        TextViewCompat.setTextAppearance(textView, id)
    }
}
