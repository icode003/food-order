package com.khane.customview

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.graphics.drawable.StateListDrawable
import android.content.res.TypedArray
import android.util.AttributeSet
import com.khane.R
import com.khane.utils.ViewUtils


class CustomConstraintLayout : ConstraintLayout {

    private var clDefaultBgColor = 0
    private var clBorderColor = 0
    private var clBorderStroke = 0
    private var clRadius = 0f
    private var clLeftTopRadius = 0f
    private var clRightTopRadius = 0f
    private var clRightBottomRadius = 0f
    private var clLeftBottomRadius = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a: TypedArray = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.CustomConstraintLayout,
            0, 0
        )
        clBorderColor = a.getColor(R.styleable.CustomConstraintLayout_clBorderColor, 0)
        clDefaultBgColor = a.getColor(R.styleable.CustomConstraintLayout_clBgColor, 0)
        clBorderStroke = a.getInt(R.styleable.CustomConstraintLayout_clBorderStroke, 0)
        clRadius = a.getDimension(R.styleable.CustomConstraintLayout_clRadius, 0f)
        clLeftTopRadius = a.getDimension(R.styleable.CustomConstraintLayout_clLeftTopRadius, 0f)
        clRightTopRadius = a.getDimension(R.styleable.CustomConstraintLayout_clRightTopRadius, 0f)
        clRightBottomRadius =
            a.getDimension(R.styleable.CustomConstraintLayout_clRightBottomRadius, 0f)
        clLeftBottomRadius =
            a.getDimension(R.styleable.CustomConstraintLayout_clLeftBottomRadius, 0f)

        if (clRadius != 0F) {
            clLeftTopRadius = clRadius
            clLeftBottomRadius = clRadius
            clRightTopRadius = clRadius
            clRightBottomRadius = clRadius
        }
        setBackGroundStates()
    }

    fun setRrDefaultBgColor(rrDefaultBgColor: Int) {
        this.clDefaultBgColor = rrDefaultBgColor
        setBackGroundStates()
    }

    private fun setBackGroundStates() {
        val states = StateListDrawable()
        states.addState(
            intArrayOf(),
            ViewUtils.setDrawableBackGround(
                clDefaultBgColor,
                clBorderColor,
                clBorderStroke,
                clLeftTopRadius,
                clRightTopRadius,
                clRightBottomRadius,
                clLeftBottomRadius
            )
        )
        background = states
    }
}
