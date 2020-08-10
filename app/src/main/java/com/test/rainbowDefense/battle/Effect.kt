package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat

class Effect (
    var x: Int,
    var y: Int,
    val width: Int,
    val height: Int,
    val bitmap_array: ArrayList<Bitmap>,
    val bitmap_size: Int,
    val duration: Int
    ){

    val lifetime: Int = bitmap_size * duration
    var time:Int = 0
    var bitmap_index = 0

    fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(
            bitmap_array.get(Math.abs(bitmap_index)),
            x.toFloat(),
            y.toFloat(),
            null
        )
    }

    init {

    }


}