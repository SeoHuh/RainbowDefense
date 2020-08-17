package com.test.rainbowDefense.battle

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

class Block(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    drawable: Drawable,
    clickDrawable : Drawable
)
    : Shape(x,y,width,height,drawable){

    var isClickable = true
    var isClick = false
    private val clickBitmapDrawable : BitmapDrawable = clickDrawable as BitmapDrawable
    var clickBitmap : Bitmap = clickBitmapDrawable.bitmap

    init {
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
    }
}