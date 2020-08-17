package com.test.rainbowDefense.battle

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Status (
    var x: Int,
    var y: Int,
    val width: Int,
    val height: Int){

    val textPaint = Paint()
    val hpPaint = Paint()
    val hpStrokePaint = Paint()

    var wave: Int = 0
    var waveMax = 5
    var cloud = 200
    var stage = 1
    var hpMax = 100
    var hp = 50


    init{
        //textPaint.isAntiAlias = true
        textPaint.color = Color.rgb(0, 0, 0)
        textPaint.textSize = height /2f
        hpPaint.color = Color.rgb(235,30,30)
        hpPaint.style = Paint.Style.FILL
        hpStrokePaint.color = Color.rgb(50,50,50)
        hpStrokePaint.style = Paint.Style.STROKE
        hpStrokePaint.strokeWidth = 10f
    }

    fun draw(canvas: Canvas?) {
        val hpPercent = hp.toFloat() / hpMax
        canvas?.drawText("Hp", x + 10f, height/2f+20f, textPaint)
        canvas?.drawRect(x + 150f, y + 10f, (x + 150f)+(400f*hpPercent), height - 20f, hpPaint)
        canvas?.drawRect(x + 150f, y + 10f, x + 550f, height - 20f, hpStrokePaint)
        canvas?.drawText("Stage:", x + 800f, height/2f+20f, textPaint)
        canvas?.drawText(stage.toString(), x + 1000f, height/2f+20f, textPaint)
        canvas?.drawText("Wave", x + 1100f, height/2f+20f, textPaint)
        canvas?.drawText(("$wave / $waveMax"), x + 1300f, height/2f+20f, textPaint)
        canvas?.drawText("Cloud", x + 1600f, height/2f+20f, textPaint)
        canvas?.drawText(cloud.toString(), x + 1800f, height/2f+20f, textPaint)
    }
}