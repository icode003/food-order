package com.khane.customview

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.khane.R
import com.khane.utils.ViewUtils

class CustomEditText : AppCompatEditText {

    private var mEtIsPhone = false
    private var mEtIsCountryCode = false
    private var mEtBackgroundColor = 0
    private var mEtBorderColor = 0
    private var mEtBorderStroke = 8
    private var mEtLeftTopRadius = 8f
    private var mEtRightTopRadius = 8f
    private var mEtRightBottomRadius = 8f
    private var mEtLeftBottomRadius = 8f
    private var mEtIsCustomBottomLine = false
    private var mEtBottomLineNormalColor = 0
    private var mEtBottomLineFocusColor = 0


    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomEditText,
            0, 0
        )
        mEtBorderColor = a.getColor(R.styleable.CustomEditText_etBorderColor, 0)
        mEtBackgroundColor = a.getColor(R.styleable.CustomEditText_etBackgroundColor, 0)
        mEtBorderStroke = a.getInt(R.styleable.CustomEditText_etBorderStroke, 0)
        mEtLeftTopRadius = a.getDimension(R.styleable.CustomEditText_etLeftTopRadius, 0f)
        mEtRightTopRadius = a.getDimension(R.styleable.CustomEditText_etRightTopRadius, 0f)
        mEtRightBottomRadius = a.getDimension(R.styleable.CustomEditText_etRightBottomRadius, 0f)
        mEtLeftBottomRadius = a.getDimension(R.styleable.CustomEditText_etLeftBottomRadius, 0f)
        mEtIsCountryCode = a.getBoolean(R.styleable.CustomEditText_etIsCountryCode, false)
        mEtIsPhone = a.getBoolean(R.styleable.CustomEditText_etIsPhone, false)
        mEtIsCustomBottomLine = a.getBoolean(R.styleable.CustomEditText_etIsCustomBottomLine, false)
        mEtBottomLineNormalColor = a.getColor(R.styleable.CustomEditText_etBottomLineNormalColor, 0)
        mEtBottomLineFocusColor = a.getColor(R.styleable.CustomEditText_etBottomLineFocusColor, 0)
        //One tap solution if all radius are same
        val etRadius = a.getDimension(R.styleable.CustomEditText_etRadius, 0f)

        if (etRadius != 0f) {
            mEtLeftTopRadius = etRadius
            mEtRightTopRadius = etRadius
            mEtLeftBottomRadius = etRadius
            mEtRightBottomRadius = etRadius
        }

        initBackground()
       /* if (mEtIsCountryCode) {
            setCountryCode()
        }
        if (mEtIsPhone) {
            setPhone()
        }*/
        if (mEtIsCustomBottomLine) {
            changeEditTextUnderlineColor(this, mEtBottomLineFocusColor, mEtBottomLineNormalColor)
        }
    }

    private fun initBackground() {
        background = ViewUtils.setDrawableBackGround(
            mEtBackgroundColor,
            mEtBorderColor,
            mEtBorderStroke,
            mEtLeftTopRadius,
            mEtRightTopRadius,
            mEtRightBottomRadius,
            mEtLeftBottomRadius
        )
    }

   /* private fun setCountryCode() {
        maxLines = 1
        filters = arrayOf<InputFilter>(LengthFilter(Limits.COUNTRY_CODE_TXT_LIMIT))
        val codeInfoController = CountryCodeInfoController(context)
        setText(
            java.lang.String.format(
                "+%s",
                codeInfoController.getUserCountryInfo().countryCode
            )
        )
        setSelection(text!!.length)
        inputType = InputType.TYPE_CLASS_NUMBER
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!s.toString().startsWith("+")) {
                    setText(String.format("+%s", text.toString().replace("+", "")))
                    setSelection(text!!.length)
                }
            }
        })
    }


    private fun setPhone() {
        maxLines = 1
        filters = arrayOf<InputFilter>(LengthFilter(Limits.PHONE_NUMBER_TXT_LIMIT))
        inputType = InputType.TYPE_CLASS_NUMBER
        imeOptions = EditorInfo.IME_ACTION_NEXT
    }*/

    private fun changeEditTextUnderlineColor(
        editText: EditText,
        focusedColor: Int,
        normalColor: Int
    ) {

        val drawableFocused = ContextCompat.getDrawable(
            editText.context,
            R.drawable.layer_bg_focused_edittext
        ) as LayerDrawable?

        val gradientDrawableFocused =
            drawableFocused?.findDrawableByLayerId(R.id.focused_layer) as GradientDrawable

        gradientDrawableFocused.setStroke(2, focusedColor)

        val drawable = ContextCompat.getDrawable(
            editText.context,
            R.drawable.layer_bg_edittext
        ) as LayerDrawable?

        val gradientDrawableNormal =
            drawable?.findDrawableByLayerId(R.id.normal_layer) as GradientDrawable
        gradientDrawableNormal.setStroke(2, normalColor)

        val states = StateListDrawable()
        states.addState(intArrayOf(android.R.attr.state_focused), drawableFocused)
        states.addState(intArrayOf(), drawable)

        editText.background = states

    }


    fun setBackgroundColorRadius(backgroundColor: Int) {
        mEtBackgroundColor = backgroundColor
        initBackground()
    }

    fun setBorderColorRadius(borderColor: Int) {
        mEtBorderColor = borderColor
        initBackground()
    }

    fun setBorderStrokeRadius(borderStroke: Int) {
        mEtBorderStroke = borderStroke
        initBackground()
    }


    fun setEtLeftTopRadius(etLeftTopRadius: Float) {
        mEtLeftTopRadius = etLeftTopRadius
        initBackground()
    }


    fun setEtRightTopRadius(etRightTopRadius: Float) {
        mEtRightTopRadius = etRightTopRadius
        initBackground()
    }

    fun setEtRightBottomRadius(etRightBottomRadius: Float) {
        mEtRightBottomRadius = etRightBottomRadius
        initBackground()
    }

    fun getEtLeftBottomRadius(): Float {
        return mEtLeftBottomRadius
    }

    fun setEtLeftBottomRadius(etLeftBottomRadius: Float) {
        this.mEtLeftBottomRadius = etLeftBottomRadius
        initBackground()
    }

    fun setRadius(
        leftTopRadius: Float,
        rightTopRadius: Float,
        rightBottomRadius: Float,
        leftBottomRadius: Float
    ) {
        mEtLeftTopRadius = leftTopRadius
        mEtRightTopRadius = rightTopRadius
        mEtRightBottomRadius = rightBottomRadius
        mEtLeftBottomRadius = leftBottomRadius
        initBackground()
    }
}