package com.test.rainbowDefense.battle

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.Toast

class Block(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    drawable: Drawable,
    clickDrawable : Drawable,
    val text:String)
    : Shape(x,y,width,height,drawable){

    var isClickable = true
    var isClick = false
    private val clickBitmapDrawable : BitmapDrawable = clickDrawable as BitmapDrawable
    var clickBitmap : Bitmap = clickBitmapDrawable.bitmap
    val textPaint = Paint()
    val textSize = width/4f

    init {
        textPaint.textSize = textSize
        textPaint.textAlign = Paint.Align.CENTER
        clickBitmap = Bitmap.createScaledBitmap(clickBitmap,width,height,false)
    }

    fun onClick() {
        isClick = true
//        TODO("블록 클릭시 동작 정의, 스킬 블록의 경우 드래그시 스킬범위 표시되도록 구현")
    }

    override fun draw(canvas: Canvas?) {
        if(isClick){
            canvas?.drawBitmap(clickBitmap,x.toFloat(),y.toFloat(),paint)
        }
        else {
            canvas?.drawBitmap(bitmap,x.toFloat(),y.toFloat(),paint)
        }
        canvas?.drawText(text,x+width/2f,y+height/2f+textSize/2f,textPaint)
    }
}