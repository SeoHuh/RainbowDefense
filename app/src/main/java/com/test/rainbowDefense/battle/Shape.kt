package com.test.rainbowDefense.battle

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

// 그릴 수 있는 객체
// 좌표와 크기, 핑, 드로어블, 이동속도, 수명을 가진다.
// 캔버스에 그리기 draw(), 한 프레임만큼 이동 move()

open class Shape (
    var x: Int,
    var y: Int,
    val width: Int,
    val height: Int,
    val drawable: Drawable){

    val ping = 120
    open var lifetime: Int = 100
    open var vx: Int = 600/120
    open var vy: Int = 0
    val paint = Paint()
    private val bitmapDrawable : BitmapDrawable = drawable as BitmapDrawable
    var bitmap : Bitmap = bitmapDrawable.bitmap

    init {
        bitmap = Bitmap.createScaledBitmap(bitmap,width,height,false)
        paint.isAntiAlias = true
        paint.color= Color.rgb(0,0,0)
        paint.style= Paint.Style.FILL
    }

    open fun draw(canvas: Canvas?){
        canvas?.drawBitmap(bitmap,x.toFloat(),y.toFloat(),paint)
    }

    open fun move(){     // 한번 이동
        x += vx
        y += vy
        lifetime--
    }
}
