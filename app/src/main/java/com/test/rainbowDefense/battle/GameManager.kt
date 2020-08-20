package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.view.MotionEvent
import android.widget.Toast
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.WaveEntity

class GameManager(val content: Context, val v: CanvasView, wave: WaveEntity) {

    // 실행중?
    var isRunning = false

    // 화면 크기 고정
    private val displayWidth = 1920
    private val displayHeight = 1080

    // 전투화면 높이
    private val battleHeight = 840

    // 메뉴(블록)화면 높이
    private val menuHeight = 240

    // 상태창 높이
    private val statusHeight = 120

    // 핸들러 쓰레드
    var handler: Handler? = Handler()
    var thread = ThreadClass()

    // 핑 (초당 프레임 수)
    val ping = 120

    // 화면 좌표
    var touchX: Float = 100f
    var touchY: Float = 500f
    var isTouch: Boolean = false

    // 블록메뉴, 웨이브, 배경, 이펙트, 화살 인스턴스
    val blockMenu = BlockMenu(content,v.block_array,0,battleHeight,displayWidth,menuHeight)
    val waveManager = WaveManager(content,v,wave,ping,displayWidth,battleHeight)
    val background = Background(displayWidth,battleHeight,statusHeight,v,content)
    private val effectManager = EffectManager(content,v,ping)
    val arrowManager = ArrowManager(content,v,effectManager,ping)


    // 초기화 ( 터치 이벤트 설정 )
    init {
        v.setOnTouchListener { v, event ->
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.performClick()
                    onStartTouchEvent(x, y)
                }
                MotionEvent.ACTION_MOVE -> {
                    onMoveTouchEvent(x, y)
                }
                MotionEvent.ACTION_UP -> {
                    upTouchEvent()
                }
            }
            true
        }
    }

    // 핸들러, 쓰레드 실행
    fun run() {
        if (!isRunning) {
            handler?.postDelayed(thread, 1000 / ping.toLong())
            isRunning = true
        }
    }
    inner class ThreadClass : Thread() {
        override fun run() {
            if(isRunning) {
                arrowManager.arrowCheck(isTouch,touchX,touchY)
                arrowManager.checkProjectile()
                arrowManager.checkCollision()
                effectManager.checkEffect()
                v.monster_array.forEach { it.move() }
                v.projectile_array.forEach { it.move() }
                waveManager.waveCheck()
                v.invalidate()
                winCheck()
                handler?.postDelayed(this, 1000 / ping.toLong())
            }
        }
    }

    // 터치 이벤트 관련
    private fun onStartTouchEvent(x: Float, y: Float) {
        touchX = x
        touchY = y
        v.cursor?.x = x.toInt()
        v.cursor?.y = y.toInt()
        isTouch = true
        checkTouch()
        v.invalidate()
    }
    private fun onMoveTouchEvent(x: Float, y: Float) {
        val dx = Math.abs(x - touchX)
        val dy = Math.abs(y - touchY)
        if (dx >= CanvasView.TOLERANCE || dy >= CanvasView.TOLERANCE) {
            touchX = x
            touchY = y
            v.cursor?.x = x.toInt()
            v.cursor?.y = y.toInt()
        }
        v.invalidate()
    }
    private fun upTouchEvent() {
        isTouch = false
    }
    fun checkTouch() { // 터치한 부분에 맞는 이벤트 실행(블록 클릭, 유닛 클릭, 적유닛 클릭 등등)
        v.block_array.forEach{  // 블록 클릭시
            val condition1: Boolean = touchX >= it.x && touchX <= it.x + it.width
            val condition2 : Boolean = touchY >= it.y && touchY <= it.y + it.height
            if(condition1 && condition2 && it.isClickable) {
                it.onClick()
                Toast.makeText(content, "$displayWidth , $displayHeight View 크기", Toast.LENGTH_SHORT).show()
//                TODO("블록 종류에 맞춰 이벤트 실행하는 코드")
            }
        }
        v.monster_array.forEach{    // 몬스터 클릭시
            val condition1: Boolean = touchX >= it.x && touchX <= it.x + it.width
            val condition2 : Boolean = touchY >= it.y && touchY <= it.y + it.height
            if(condition1 && condition2) {
                Toast.makeText(content, "$displayWidth , $displayHeight View 크기", Toast.LENGTH_SHORT).show()
//                TODO("몬스터 정보 작게 표시")
            }
        }
//        TODO("건물 또는 유닛 클릭시 정보 표시하는 코드")
    }

    // 승리, 패배 조건 확인
    fun winCheck(){
        if(v.status?.wave == v.status?.waveMax      // 웨이브 종료
            && v.monster_array.isNullOrEmpty()) {   // 몬스터 모두 사망
            win()
            isRunning = false
        }
        else if(v.status!!.hp<=0){      // 체력이 0 이하로 내려갔을 때
            lose()
            isRunning = false
        }
    }
    fun win() {
        Toast.makeText(content,"승리!",Toast.LENGTH_SHORT).show()
//        TODO("보상을 계산하여, 데이터베이스에 적용, Dialog를 띄워서 결과창 출력, 버튼을 누르면 Loby 액티비티로 돌아감")
    }
    fun lose() {
        Toast.makeText(content,"승리!",Toast.LENGTH_SHORT).show()
//        TODO("보상을 계산하여, 데이터베이스에 적용, Dialog를 띄워서 결과창 출력, 버튼을 누르면 Loby 액티비티로 돌아감")

    }
}