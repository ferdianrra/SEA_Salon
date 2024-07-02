package com.dicoding.seasalon.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.seasalon.R

class MyTransparentButton: AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    private var backgroundTransparent : Drawable
    private var textColors: Int = 0

    init {
        backgroundTransparent = ContextCompat.getDrawable(context, R.drawable.bg_btn_transparant) as Drawable
        textColors = ContextCompat.getColor(context, R.color.white_bg)
        setGravity(android.view.Gravity.CENTER)
        textAlignment = TEXT_ALIGNMENT_CENTER
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = backgroundTransparent
        setTextColor(textColors)
    }
}