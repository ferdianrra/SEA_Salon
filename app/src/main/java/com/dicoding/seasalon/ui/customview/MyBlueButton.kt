package com.dicoding.seasalon.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.seasalon.R

class MyBlueButton : AppCompatButton {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    private var   backgroundBlue : Drawable

    init {
        backgroundBlue = ContextCompat.getDrawable(context, R.drawable.bg_btn_blue) as Drawable
        setGravity(android.view.Gravity.CENTER)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background =   backgroundBlue
    }
}