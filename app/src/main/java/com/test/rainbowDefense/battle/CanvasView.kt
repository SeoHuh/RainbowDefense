package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.*
import android.view.View
import android.util.AttributeSet

// 캔버스 뷰 클래스
// 게임내 다양한 객체들을 그리기 위한 커스텀 뷰
// 그려야할 다양한 객체들을 미리 선언하고, 게임매니저 내부에서 객체를 추가해주면 후에 그리는 뷰이다.

class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    // Paint 와 Path
    var mPath : Path = Path()
    var mPaint : Paint = Paint()

    // 그려야할 객체들
    var status: Status? = null
    var skillShape: Shape? = null
    var projectile_array = arrayListOf<Projectile>()
    var unit_array = arrayListOf<MyUnit>()
    var monster_array = arrayListOf<Monster>()
    var effect_array = arrayListOf<Effect>()
    var block_array = arrayListOf<Block>()
    var cursor: Shape? = null
    var background: Shape? = null
    var shadowMode: Shape? = null

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
        monster_array.forEach{it.draw(canvas)}
        unit_array.forEach{it.draw(canvas)}
        projectile_array.forEach{it.draw(canvas)}
        effect_array.forEach {it.draw(canvas)}
        shadowMode?.draw(canvas)
        skillShape?.draw(canvas)
        status?.draw(canvas)
        block_array.forEach {it.draw(canvas)}
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
        val DRAGTOLERANCE = 10f
    }
}