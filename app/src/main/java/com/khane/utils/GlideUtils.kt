package com.khane.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide

object GlideUtils {

    fun loadImage(
        context: Context?,
        resourceUrl: String,
        placeHolderId: Int,
        errorResId: Int,
        imageView: ImageView
    ) {
        context?.let {
            Glide
                .with(it)
                .load(resourceUrl)
                .into(imageView)
        }
    }

    fun loadImage(
        context: Context?,
        resourceUrl: String,
        placeHolderId: Drawable?,
        errorResId: Drawable?,
        imageView: ImageView
    ) {
        context?.let {
            Glide
                .with(it)
                .load(resourceUrl)
                .placeholder(placeHolderId)
                .error(errorResId)
                .into(imageView)
        }
    }

}