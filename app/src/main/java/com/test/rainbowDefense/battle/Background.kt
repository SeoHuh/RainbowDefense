package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.test.rainbowDefense.R

class Background (
    val displayWidth : Int,
    val battleHeight : Int,
    val statusHeight : Int,
    val v : CanvasView,
    val content : Context) {

    val textPaint = Paint()
    val hpPaint = Paint()
    val hpStrokePaint = Paint()

    // 리소스 Drawable
    val cursorDrawable = v.resources.getDrawable(R.drawable.cursor1, content.theme)
    val backgroundDrawable = v.resources.getDrawable(R.drawable.battleback_2, content.theme)

    val status = Status(0,0,displayWidth,statusHeight)

    init {
        v.status = status
        v.cursor = Shape(
            100,
            battleHeight/2,
            12 * 3,
            19 * 3,
            cursorDrawable
        )
        v.background = Shape(
            0,
            0,
            displayWidth,
            battleHeight,
            backgroundDrawable
        )
    }
}