package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import com.test.rainbowDefense.R

// 사용자 상태 정의
// 성 HP 상태, 웨이브, 스테이지, 자원 출력

class Status (
    var x: Int,
    var y: Int,
    val width: Int,
    val height: Int,
    val content: Context,
    val v: CanvasView,
    font:Typeface,
    castleHp: Int,
    manaMax: Int){

    val pauseButtonDrawable = v.resources.getDrawable(R.drawable.pause, content.theme)
    val cloudDrawable = v.resources.getDrawable(R.drawable.cloud,content.theme)
    val cloudBitmapDrawable = cloudDrawable as BitmapDrawable
    var cloudBitmap = cloudBitmapDrawable.bitmap
    val manaDrawable = v.resources.getDrawable(R.drawable.blue_circle,content.theme)
    val manaBitmapDrawable = manaDrawable as BitmapDrawable
    var manaBitmap = manaBitmapDrawable.bitmap

    val textPaint = Paint()
    val hpPaint = Paint()
    val hpStrokePaint = Paint()

    val padding = height / 8f
    val textHeight = height * 0.6f
    val cloudWidth = (height * 0.7f).toInt()
    val manaWidth = (height * 0.7f).toInt()
    val cloudHeight = (height * 0.5f).toInt()
    val manaHeight = (height * 0.7f).toInt()
    val cloudPadding = height * 0.25f
    val manaPadding = height * 0.15f
    val pauseButtonSize = (height * 1.5f).toInt()

    var wave: Int = 0
    var waveMax = 5
    var cloud = 100
    var stage = 1
    var hpMax = castleHp
    var hp = castleHp
    var stageReward = 0
    var monsterReward = 0
    var mana = manaMax

    init{
        v.status = this
        manaBitmap = Bitmap.createScaledBitmap(manaBitmap,manaWidth,manaHeight,false)
        cloudBitmap = Bitmap.createScaledBitmap(cloudBitmap,cloudWidth,cloudHeight,false)

        //textPaint.isAntiAlias = true
        textPaint.color = Color.rgb(0, 0, 0)
        textPaint.textSize = height /2f
        textPaint.typeface = font
        hpPaint.color = Color.rgb(235,30,30)
        hpPaint.style = Paint.Style.FILL
        hpStrokePaint.color = Color.rgb(50,50,50)
        hpStrokePaint.style = Paint.Style.STROKE
        hpStrokePaint.strokeWidth = 10f
        v.pause_button = Shape((x + 2 * padding + width * 0.92f).toInt(),padding.toInt(),pauseButtonSize,pauseButtonSize,pauseButtonDrawable)
    }

    fun draw(canvas: Canvas?) {
        val hpPercent = hp.toFloat() / hpMax
        textPaint.color = Color.BLACK
        canvas?.drawText("Hp", x + padding, textHeight, textPaint)
        canvas?.drawRect(x + padding + width * 0.05f, y + padding, (x + padding + width * 0.05f)+(width * 0.3f * hpPercent), height - 2*padding, hpPaint)
        canvas?.drawRect(x + padding + width * 0.05f, y + padding, x + padding + width * 0.35f, height - 2*padding, hpStrokePaint)
        canvas?.drawText("Stage:", x + 3 * padding + width * 0.35f, textHeight, textPaint)
        canvas?.drawText(stage.toString(), x + 3 * padding + width * 0.42f, textHeight, textPaint)
        canvas?.drawText("Wave", x + 3 * padding + width * 0.46f,textHeight, textPaint)
        canvas?.drawText(("$wave / $waveMax"), x + 3 * padding + width * 0.54f, textHeight, textPaint)
        canvas?.drawBitmap(cloudBitmap,x + 3 * padding + width * 0.6f, cloudPadding,textPaint)
        textPaint.color = Color.WHITE
        canvas?.drawText(cloud.toString(), x + 3 * padding + width * 0.68f, textHeight, textPaint)
        canvas?.drawBitmap(manaBitmap,x + 3 * padding + width * 0.76f, manaPadding, textPaint)
        textPaint.color = Color.CYAN
        canvas?.drawText(mana.toString(), x+ 3 * padding + width * 0.84f,textHeight,textPaint)
    }
}