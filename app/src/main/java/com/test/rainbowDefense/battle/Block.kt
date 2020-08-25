package com.test.rainbowDefense.battle

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

// 클릭 가능한 버튼 UI
// 유닛 블록, 스킬 블록, 건물 블록등으로 클릭 가능한 블록 클래스이다.

class Block(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    drawable: Drawable,
    clickDrawable: Drawable,
    val text: String
) : Shape(x, y, width, height, drawable) {

    private var listener: BlockListener? = null
    fun setOnlistner(listener: BlockListener) {
        this.listener = listener
    }

    interface BlockListener {
        fun onClick()
    }

    var isClickable = true
    var isClick = false
    private val clickBitmapDrawable: BitmapDrawable = clickDrawable as BitmapDrawable
    var clickBitmap: Bitmap = clickBitmapDrawable.bitmap
    val textPaint = Paint()
    val textSize = width / 4f
    var innerBitmap: Bitmap? = null
    val padding = width / 22f
    val costWidth = padding * 7f
    val costHeight = padding * 5f
    val costSize = padding * 3f
    var costBitmap: Bitmap? = null
    var costString: String? = null
    val costPaint = Paint()

    init {
        textPaint.textSize = textSize
        textPaint.textAlign = Paint.Align.CENTER
        costPaint.textSize = costSize
        costPaint.textAlign = Paint.Align.CENTER
        clickBitmap = Bitmap.createScaledBitmap(clickBitmap, width, height, false)
    }

    fun onClick() {
        isClick = true
        listener?.onClick()
//        TODO("블록 클릭시 동작 정의, 스킬 블록의 경우 드래그시 스킬범위 표시되도록 구현")
    }

    override fun draw(canvas: Canvas?) {
        if (isClick) {
            canvas?.drawBitmap(clickBitmap, x.toFloat(), y.toFloat(), paint)
        } else {
            canvas?.drawBitmap(bitmap, x.toFloat(), y.toFloat(), paint)
        }
        canvas?.drawText(text, x + width / 2f, y + height / 2f + textSize / 2f, textPaint)

        innerBitmap?.let{
            canvas?.drawBitmap(it, x + 3 * padding, y + 3 * padding, paint)
        }
        costBitmap?.let{
            canvas?.drawBitmap(it, x+width-costWidth,y+height-costHeight, paint)
        }
        costString?.let{
            canvas?.drawText(it, x+width-costWidth/2f, y+height-costHeight/2f+costSize/2f,costPaint)
        }
    }
}