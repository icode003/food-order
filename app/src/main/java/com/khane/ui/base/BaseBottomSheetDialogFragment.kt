package com.khane.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.khane.R
import com.khane.utils.ErrorMessageUtils

abstract class BaseBottomSheetDialogFragment<T : ViewBinding> : BottomSheetDialogFragment() {

    //    private var mBottomSheetBehavior: BottomSheetBehavior<View>? = null

    // Bhargav Savasani : 9/3/21 set Custom Style
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }*/

    private var _binding: T? = null

    protected val mBinding: T
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)

        if (isCustomHeight()) {
            dialog?.setOnShowListener { dialog ->
                val d = dialog as BottomSheetDialog
                val bottomSheetInternal: View? = d.findViewById<View>(R.id.design_bottom_sheet)
                bottomSheetInternal?.let { view ->
//                BottomSheetBehavior.from(view).state = BottomSheetBehavior.STATE_EXPANDED
                    BottomSheetBehavior.from(view).peekHeight = getBottomSheetViewHeight()
                }
            }
        }
        _binding = createViewBinding(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniControl(savedInstanceState)
        handleViews()
        setListeners()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    protected abstract fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): T

    abstract fun isCustomHeight(): Boolean
    //return false

    abstract fun getBottomSheetViewHeight(): Int
    //(int) (getScreenHeight(getActivity()) * 0.85)

    abstract fun iniControl(savedInstanceState: Bundle?)

    abstract fun handleViews()

    abstract fun setListeners()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun hideKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideKeyboard()
        }
    }

    /* fun showAlertDialog(
         message: String,
         dialogOkListener: BaseActivity.DialogOkListener? = null,
         neutralText: String = getString(R.string.label_ok)
     ) {
         navigator.showAlertDialog(message, neutralText, dialogOkListener)
     }*/

    fun showErrorMessage(message: String, view: View? = dialog?.window?.decorView) {
        view?.let {
            /*  (requireActivity() as BaseActivity).hideKeyboard()
              val make = ErrorMessageUtils.getErrorSnackbarDefault(
                  requireContext(),
                  it,
                  message,
                  null
              )
              make.show()*/
            ErrorMessageUtils.getCustomToast(requireActivity(), message)
        }

    }
    /*open fun setBottomSheetBehaviour(view: View) {


        mBottomSheetBehavior = BottomSheetBehavior.from(view)
        *//*val bottomSheetCallback: BottomSheetCallback = object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> Log.d("", ": ")
                    BottomSheetBehavior.STATE_DRAGGING -> Log.d("", ": ")
                    BottomSheetBehavior.STATE_EXPANDED -> Log.d("", ": ")
                    BottomSheetBehavior.STATE_HIDDEN -> Log.d("", ": ")
                    BottomSheetBehavior.STATE_SETTLING -> Log.d("", ": ")
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> Log.d("", ": ")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset <= 0.4f) {
                    dismiss()
                }
            }
        }
        mBottomSheetBehavior?.addBottomSheetCallback(bottomSheetCallback)*//*
        mBottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        mBottomSheetBehavior?.peekHeight =
            0 //Sets the height of the bottom sheet when it is collapsed.
    }*/

    /*open fun lockBottomSheet() {
        if (mBottomSheetBehavior != null && mBottomSheetBehavior is LockableBottomSheetBehavior) {
            (mBottomSheetBehavior as LockableBottomSheetBehavior<View?>).setLocked(true)
        }
    }

    open fun releaseBottomSheet() {
        if (mBottomSheetBehavior != null && mBottomSheetBehavior is LockableBottomSheetBehavior) {
            (mBottomSheetBehavior as LockableBottomSheetBehavior<View?>).setLocked(
                false
            )
        }
    }*/
}