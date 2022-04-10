package com.khane.ui.base

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.khane.R
import com.khane.core.AppPreferences
import com.khane.core.Session
import com.khane.di.HasComponent
import com.khane.di.component.ActivityComponent
import com.khane.di.component.FragmentComponent
import com.khane.di.module.FragmentModule
import com.khane.ui.imagepicker.ImageVideoPickerDialog
import com.khane.ui.manager.Navigator
import com.khane.utils.CustomTypefaceSpan
import com.khane.utils.StringUtils.getSpannableString
import com.khane.utils.Utils
import com.khane.utils.ViewUtils
import javax.inject.Inject

/**
 * Created by hlink21 on 25/4/16.
 */
abstract class BaseFragment<T : ViewBinding> : Fragment(), HasComponent<FragmentComponent> {

    protected lateinit var toolbar: HasToolbar

    @Inject
    lateinit var session: Session

    @Inject
    public lateinit var navigator: Navigator

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val component: FragmentComponent
        get() {
            return getComponent(ActivityComponent::class.java).plus(FragmentModule(this))
        }

    private var _binding: T? = null

    protected val mBinding: T
        get() = _binding!!

//    lateinit var mBinding: T
//    private var isDestroyCall = false
//    var hasInitializedRootView = false


  /*  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasInitializedRootView = false
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      /*  hasInitializedRootView = ::mBinding.isInitialized
        if (!hasInitializedRootView) {
            mBinding = createViewBinding(inflater, container, false)
        }
        return mBinding.root*/
        _binding = createViewBinding(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (!hasInitializedRootView) {
//            hasInitializedRootView = true
            bindData()
//        }
    }

    private fun <C> getComponent(componentType: Class<C>): C {
        return componentType.cast((activity as HasComponent<C>).component)
    }

    override fun onAttach(context: Context) {
        inject(component)
        super.onAttach(context)

        if (activity is HasToolbar)
            toolbar = activity as HasToolbar


    }

    fun <T : BaseFragment<*>> getParentFragment(targetFragment: Class<T>): T? {
        if (parentFragment == null) return null
        try {
            return targetFragment.cast(parentFragment)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return null
    }

    open fun onShow() {

    }

    open fun onBackActionPerform(): Boolean {
        return true
    }

    open fun onViewClick(view: View) {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /**
     * This method is used for binding view with your binding
     */
    protected abstract fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): T

    protected abstract fun inject(fragmentComponent: FragmentComponent)
    protected abstract fun bindData()


    fun hideKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideKeyboard()
        }
    }

    fun showKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showKeyboard()
        }
    }

    fun showLoader() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showLoader()
        }
    }

    fun hideLoader() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideLoader()
        }
    }

    public fun onError(throwable: Throwable) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).onError(throwable)
        }
    }

    fun showToast(string: String) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showToast(string)
        }
    }

    fun openImageOrVideoPickDefault(
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
            childFragmentManager
        )
    }

    fun showErrorMessage(message: String, snackBarListener: BaseActivity.SnackBarListener? = null) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showErrorMessage(message, snackBarListener)
        }
    }

    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(requireContext(), id)
    }

    fun getColor(@ColorRes id: Int): Int {
        return requireContext().getColor(id)
    }

    fun setTextAppearance(textView: TextView, @StyleRes id: Int) {
        ViewUtils.setTextAppearance(textView, id)
    }

    fun getSpan(
        string: CharSequence,
        typeface: Typeface,
        startPos: Int,
        endPos: Int,
        color: Int
    ): SpannableString {
        return getSpannableString(string, typeface, startPos, endPos, color)
    }

    fun getSpan(
        string: CharSequence,
        firstTypeface: Typeface,
        firstStartPos: Int,
        firstEndPos: Int,
        firstColor: Int,
        secondTypeface: Typeface,
        secondStartPos: Int,
        secondEndPos: Int,
        secondColor: Int
    ): SpannableString {
        val spannableString =
            getSpan(string, firstTypeface, firstStartPos, firstEndPos, firstColor)
        spannableString.setSpan(
            CustomTypefaceSpan("", secondTypeface),
            secondStartPos,
            secondEndPos,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        if (secondColor != 0) {
            //Set font color
            spannableString.setSpan(
                ForegroundColorSpan(secondColor),
                secondStartPos,
                secondEndPos,
                0
            ) // set color
        }
        return spannableString
    }

    fun getSpan(
        string: CharSequence,
        firstTypeface: Typeface,
        firstStartPos: Int,
        firstEndPos: Int,
        firstColor: Int,
        secondTypeface: Typeface,
        secondStartPos: Int,
        secondEndPos: Int,
        secondColor: Int,
        thirdTypeface: Typeface,
        thirdStartPos: Int,
        thirdEndPos: Int,
        thirdColor: Int
    ): SpannableString {
        val spannableString =
            getSpan(
                string,
                firstTypeface,
                firstStartPos,
                firstEndPos,
                firstColor,
                secondTypeface,
                secondStartPos,
                secondEndPos,
                secondColor
            )
        spannableString.setSpan(
            CustomTypefaceSpan("", thirdTypeface),
            thirdStartPos,
            thirdEndPos,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        if (thirdColor != 0) {
            //Set font color
            spannableString.setSpan(
                ForegroundColorSpan(thirdColor),
                secondStartPos,
                secondEndPos,
                0
            ) // set color
        }
        return spannableString
    }


    fun setTintColor(@ColorRes id: Int, imageView: ImageView) {
        ViewUtils.setTintColor(id, imageView)
    }
    public fun snakeBar(message:String){
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        val layout = snackbar.view as Snackbar.SnackbarLayout
        snackbar.view.setBackgroundResource(R.drawable.bg_toast_top)
        layout.setPadding(0, 0, 0, 0)
        snackbar.show()
    }

}
