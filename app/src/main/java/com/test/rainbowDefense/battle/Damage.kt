package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable

class Damage(
    var x: Float,
    var y: Float,
    val text: String,
    val myColor: Int,
    val font:Typeface,
    ping: Int)  {

    val textPaint = Paint()
    var lifetime = ping /3
    val vy = -1.5f

    init{
        textPaint.color = Color.rgb(0, 0, 0)
        textPaint.textSize = 30f
        textPaint.typeface = font
    }

    fun draw(canvas: Canvas?){
        textPaint.color = myColor
        canvas?.drawText(text, x, y, textPaint)
    }
    fun move(){
        lifetime--
        y += vy
    }
}