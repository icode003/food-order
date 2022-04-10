package com.khane.constance

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.MenuItem

abstract class Callbacks {

    interface OnAppbarOffsetListener {
        fun inBetween()
        fun onCollapsed()
        fun onExpanded()
    }

    interface OnItemSelectedListener {
        fun onItemSelected(item: MenuItem)
    }

    interface OnGetBitmapListener {
        fun onSuccess(bitmap: Bitmap, intent: Intent?)
        fun onFail(errorDrawable: Drawable?, intent: Intent?)
    }

}