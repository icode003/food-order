package com.khane.utils

import android.content.Context
import android.graphics.Paint
import android.text.*
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout

fun EditText?.getString(): String {
    this?.text?.let {
        return it.toString().trim()
    }
    return ""
}

fun TextInputLayout?.getString(): String {
    this?.editText?.text?.let {
        return it.toString().trim()
    }
    return ""
}

fun TextView.getString(default: String): String {
    this.text?.let {
        if (it.toString().trim().isNotEmpty()) {
            return it.toString()
        }
    }
    return default
}

fun Context.getColorFromResource(id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun EditText.showPassword() {
    //Show Password
    this.transformationMethod = HideReturnsTransformationMethod.getInstance()
}

fun EditText.hidePassword() {
    //Show Password
    this.transformationMethod = PasswordTransformationMethod.getInstance()
}

fun EditText.setCountryCode() {
    maxLines = 1
    setText("+1")
    setSelection(text!!.length)
    inputType = InputType.TYPE_CLASS_NUMBER
    ViewUtils.disableEditTexts(false, this)
    /* addTextChangedListener(object : TextWatcher {
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
     })*/
}

fun AppCompatCheckBox.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Selection.setSelection((view as AppCompatCheckBox).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod =
        LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun AppCompatTextView.setUnderLineTextView(text: String) {
    this.paintFlags = this.paintFlags and Paint.UNDERLINE_TEXT_FLAG
    val content = SpannableString(text)
    content.setSpan(UnderlineSpan(), 0, text.length, 0)
    this.text = content
}

/*
private fun setPhone() {
    maxLines = 1
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(Limits.PHONE_NUMBER_TXT_LIMIT))
    inputType = InputType.TYPE_CLASS_NUMBER
    imeOptions = EditorInfo.IME_ACTION_NEXT
}
*/
