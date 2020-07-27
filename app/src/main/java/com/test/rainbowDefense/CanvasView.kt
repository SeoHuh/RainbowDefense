package com.test.rainbowDefense

import android.content.Context
import android.graphics.*
import android.view.View
import android.util.AttributeSet

class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    // Paint 와 Path
    var mPath : Path = Path()
    var mPaint : Paint = Paint()

    // 그려야할 객체들
    var projectile_array = arrayListOf<Projectile>()
    var monster_array = arrayListOf<Monster>()
    var cursor: Shape? = null
    var background: Shape? = null
    var mountain: Shape? = null
    var trees: Shape? = null

    // 초기화 (Paint 초기설정)
    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.rgb(255,0,216)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = 4f
    }

    // 그리기 (invalidate가 호출 될 때마다 같이 호출된다.)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background?.draw(canvas)
        mountain?.draw(canvas)
        trees?.draw(canvas)
        monster_array.forEach{it.draw(canvas)}
        projectile_array.forEach{it.draw(canvas)}
        cursor?.draw(canvas)
    }

    // 클릭 이벤트 수행
    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    // static 변수
    companion object {
        val TOLERANCE = 5f
    }
}