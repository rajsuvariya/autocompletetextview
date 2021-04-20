package com.rtsfinancial.carrierpro

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatAutoCompleteTextView

class RNAutoCompleteTextView(context: Context) : AppCompatAutoCompleteTextView(context) {
    init {
        inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        setTextColor(Color.parseColor("#333333"))
        textSize = 14f
        val value = 8
        val dpValue = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value.toFloat(),
                context.resources.displayMetrics).toInt()
        setPadding(0, paddingTop, 0, dpValue)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(0, 30, 0, 30)
        this.layoutParams = layoutParams
        background.setTint(Color.parseColor("#d3d3d3"))
    }
}