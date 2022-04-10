package com.khane.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.TextViewCompat
import com.google.android.material.appbar.AppBarLayout


object ViewUtils {

    private var screenWidth = 0
    private var screenHeight = 0
    /*fun clearLightStatusBar(activity: Activity) {
        val window = activity.window

    }*/

    fun setStatusBarWithBlackIcon(activity: Activity, @ColorRes color: Int) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }*/
        }
        window.statusBarColor = color
    }

    fun setWhiteStatusBarWithBlackIcon(activity: Activity) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }*/
        }
        window.statusBarColor = Color.WHITE
    }

    fun setStatusBarColor(activity: Activity, color: Int) {
        activity.window.statusBarColor = color
    }

    fun setTransparentStatusBar(activity: Activity) {
        /*// Hide status bar
getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
// Show status bar
getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        val window = activity.window
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    fun hideSystemUI(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.setDecorFitsSystemWindows(false)
            activity.window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }


    fun disableEditTexts(
        disableClickEventsToo: Boolean,
        vararg editTexts: EditText?
    ) {
        if (editTexts.isNotEmpty()) {
            for (editText in editTexts) {
                editText?.isClickable = true
                editText?.isFocusable = false
                if (disableClickEventsToo) {
                    editText?.isEnabled = false
                }
                editText?.isCursorVisible = false
                editText?.keyListener = null
                editText?.isFocusableInTouchMode = false
                //        editText.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    fun setAppbarOffsetListener(
        appBar: AppBarLayout,
        toolbar: Toolbar?,
        onAppbarOffsetListener: com.khane.constance.Callbacks.OnAppbarOffsetListener
    ) {
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout: AppBarLayout, verticalOffset: Int ->

            if (toolbar != null) {
                /*   val toolBarHeight = toolbar.measuredHeight
                   val appBarHeight = appBarLayout.measuredHeight*/
                /* val f =
                     (appBarHeight.toFloat() - toolBarHeight + verticalOffset) / (appBarHeight.toFloat() - toolBarHeight) * 255*/
            }
            if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                onAppbarOffsetListener.onCollapsed()
            } else {
                onAppbarOffsetListener.onExpanded()
            }
        })
    }


    fun setDrawableBackGround(
        bgcolor: Int,
        brdcolor: Int,
        borderStroke: Int,
        leftTopRadius: Float,
        rightTopRadius: Float,
        rightBottomRadius: Float,
        leftBottomRadius: Float
    ): GradientDrawable {
        val gdDefault = GradientDrawable()
        gdDefault.setColor(bgcolor)
        gdDefault.setStroke(borderStroke, brdcolor)
        gdDefault.cornerRadii = floatArrayOf(
            leftTopRadius,
            leftTopRadius,
            rightTopRadius,
            rightTopRadius,
            rightBottomRadius,
            rightBottomRadius,
            leftBottomRadius,
            leftBottomRadius
        )
        return gdDefault
    }

    fun getURLForResource(context: Context, appId: String, resourceId: Int): String {
        //ViewUtils.getURLForResource(context, BuildConfig.APPLICATION_ID, R.drawable.ic_temp_business_default)
        return Uri.parse("android.resource://$appId/$resourceId").toString()
    }

    fun getResourceId(context: Context, resourceName: String): Int {
        return context.resources.getIdentifier(
            resourceName, "drawable",
            context.getPackageName()
        )
    }

    fun getScreenHeight(c: Context): Int {
        if (screenHeight == 0) {
            val wm = c.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)
            screenHeight = size.y
        }

        return screenHeight
    }

    fun getScreenWidth(c: Context): Int {
        if (screenWidth == 0) {
            val wm = c.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)
            screenWidth = size.x
        }

        return screenWidth
    }

    fun getTimeFromDuration(duration: Long): String {
        val videoTime: String
        val dur = duration.toInt()
        val hrs = dur / 3600000
        val mns = dur / 60000 % 60000
        val scs = dur % 60000 / 1000
        videoTime = if (hrs > 0) {
            String.format("%02d:%02d:%02d", hrs, mns, scs)
        } else {
            String.format("%02d:%02d", mns, scs)
        }
        return videoTime
    }

    fun setTintColor(color: Int, imageView: ImageView) {
        imageView.setColorFilter(
            color,
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
    }

    fun setTextAppearance(textView: TextView, @StyleRes id: Int) {
        TextViewCompat.setTextAppearance(textView, id)
    }

    fun dpToPx(context: Context, dp: Float): Float {
        return dp * context.getResources().getDisplayMetrics().density
    }

    fun pxToDp(context: Context, px: Float): Float {
        return px / context.getResources().getDisplayMetrics().density
    }
}