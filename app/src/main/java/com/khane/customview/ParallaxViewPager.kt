package com.khane.customview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.viewpager.widget.ViewPager
import com.khane.R
import java.lang.IllegalArgumentException
import android.view.MotionEvent


class ParallaxViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private var src = Rect()
    private val dst = Rect()
    private var mBitmap: Bitmap? = null
    private var ratio = 0f
    private var speedRatio = 0.5f
    private var bitmapScale = 0.0f
    private var oldCount = 0
    var isUserInputEnabled = true

    var mOnPageChangeListener: SimpleOnPageChangeListener = object : SimpleOnPageChangeListener() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            if (mBitmap != null) {
                val offset =
                    (position.toFloat() + positionOffset) * this@ParallaxViewPager.measuredWidth.toFloat()
                src.left = (offset * ratio * speedRatio).toInt()
                src.right =
                    (src.left.toFloat() + this@ParallaxViewPager.measuredWidth.toFloat() / bitmapScale).toInt()
                dst.left = offset.toInt()
                dst.right = dst.left + this@ParallaxViewPager.measuredWidth
                this@ParallaxViewPager.invalidate()
            }
        }
    }

    init {
        if (this.background != null && this.background is BitmapDrawable) {
            mBitmap = (this.background as BitmapDrawable).bitmap
        }
        val array = context.obtainStyledAttributes(attrs, R.styleable.ParallaxViewPager, 0, 0)
        try {
            speedRatio = array.getFloat(R.styleable.ParallaxViewPager_speedRatio, 0.5f)
        } finally {
            array.recycle()
        }
        this.initSource()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addOnPageChangeListener(this.mOnPageChangeListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeOnPageChangeListener(this.mOnPageChangeListener)
    }

    override fun setBackgroundResource(@DrawableRes resid: Int) {
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(this.resources, resid)
            this.initSource()
        }
    }

    override fun setBackground(background: Drawable) {
        if (mBitmap == null) {
            mBitmap = (background as BitmapDrawable).bitmap
            this.initSource()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (this.adapter != null && this.adapter!!.count > 0 && mBitmap != null) {
            val curCount = this.adapter!!.count
            if (curCount != oldCount) {
                this.calculateRatio()
            }
            oldCount = curCount
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        dst.left = 0
        dst.right = w
        dst.top = 0
        dst.bottom = h
    }

    private fun calculateRatio() {
        if (mBitmap!!.width < this.measuredWidth) {
            Log.w(
                "ParallaxViewPager",
                "Invalidate background bitmap, bitmap width must larger than ViewPager width"
            )
        }
        bitmapScale = this.measuredHeight.toFloat() / mBitmap!!.height.toFloat()
        src.right = (this.measuredWidth.toFloat() / bitmapScale).toInt()
        val sectionWidth = mBitmap!!.width / this.adapter!!.count
        ratio = sectionWidth.toFloat() / this.measuredWidth.toFloat()
        val finalSourceRight = (this.measuredWidth.toFloat() / bitmapScale + ((this.adapter!!
            .count - 1) * this.measuredWidth).toFloat() * ratio * speedRatio).toInt()
        if (finalSourceRight > mBitmap!!.width) {
            speedRatio =
                (mBitmap!!.width.toFloat() - this.measuredWidth.toFloat() / bitmapScale) / (((this.adapter!!
                    .count - 1) * this.measuredWidth).toFloat() * ratio)
            speedRatio = if (speedRatio <= 0.0f) 1.0f else speedRatio
        }
    }

    private fun initSource() {
        mBitmap?.let { bitmap ->
            src = Rect()
            src.top = 0
            src.bottom = bitmap.height
            src.left = 0
        }
    }

    fun getSpeedRatio(): Float {
        return speedRatio
    }

    fun setSpeedRatio(speedRatio: Float) {
        if (speedRatio in 0f..1f) {
            this.speedRatio = speedRatio
        } else {
            throw IllegalArgumentException("Illegal argument: speedRatio must be between 0 and 1")
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mBitmap?.let {
            canvas.drawBitmap(it, src, dst, null as Paint?)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (this.isUserInputEnabled) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return if (this.isUserInputEnabled) {
            super.onInterceptTouchEvent(event)
        } else false
    }
}