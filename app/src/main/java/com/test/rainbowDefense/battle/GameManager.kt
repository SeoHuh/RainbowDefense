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
    val displayWidth = 1920
    val displayHeight = 1080

    // 전투화면 높이
    val battleHeight = 840

    // 메뉴(블록)화면 높이
    val menuHeight = 240

    // 상태창 높이
    val statusHeight = 120

    // 핸들러 쓰레드
    var handler: Handler? = Handler()
    var thread = ThreadClass()

    // 핑 (초당 프레임 수)
    val ping = 120

    // 화면 좌표
    var touchX: Float = 100f
    var touchY: Float = 500f
    var isTouch: Boolean = false

    // 블록메뉴, 웨이브, 배경(상태포함) 인스턴스
    val blockMenu = BlockMenu(content,v.block_array,0,battleHeight,displayWidth,menuHeight)
    val waveManager = WaveManager(content,v,wave,ping,displayWidth,battleHeight)
    val background = Background(displayWidth,battleHeight,statusHeight,v,content)

    // 이펙트(애니메이션) 리소스 (추후 EffectManager 클래스로 옮길 예정)
    val effectBitmaps = arrayListOf<Bitmap>();
    val effectId = arrayListOf(
        R.drawable.blueish_flame_0001,
        R.drawable.blueish_flame_0002,
        R.drawable.blueish_flame_0003,
        R.drawable.blueish_flame_0004,
        R.drawable.blueish_flame_0005,
        R.drawable.blueish_flame_0006,
        R.drawable.blueish_flame_0007,
        R.drawable.blueish_flame_0008,
        R.drawable.blueish_flame_0009,
        R.drawable.blueish_flame_0010,
        R.drawable.blueish_flame_0011,
        R.drawable.blueish_flame_0012,
        R.drawable.blueish_flame_0013,
        R.drawable.blueish_flame_0014,
        R.drawable.blueish_flame_0015,
        R.drawable.blueish_flame_0016,
        R.drawable.blueish_flame_0017,
        R.drawable.blueish_flame_0018,
        R.drawable.blueish_flame_0019,
        R.drawable.blueish_flame_0020,
        R.drawable.blueish_flame_0021,
        R.drawable.blueish_flame_0022,
        R.drawable.blueish_flame_0023,
        R.drawable.blueish_flame_0024,
        R.drawable.blueish_flame_0025,
        R.drawable.blueish_flame_0026,
        R.drawable.blueish_flame_0027,
        R.drawable.blueish_flame_0028,
        R.drawable.blueish_flame_0029,
        R.drawable.blueish_flame_0030
    )

    // 화살 딜레이타임, 카운터 (추후 Arrow 클래스로 옮길 예정)
    var arrowTime: Int = (0.1 * ping).toInt()
    var arrowCounter: Int = 0


    // 초기화
    init {
        effectId.forEach{
            val bitmap = BitmapFactory.decodeResource(v.resources,it)
            effectBitmaps.add(Bitmap.createScaledBitmap(bitmap,200,200,false))
        }
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
                arrowCheck()
                waveManager.waveCheck()
                checkProjectile(v.projectile_array)
                checkCollision(v.monster_array, v.projectile_array)
                checkEffect(v.effect_array)
                v.monster_array.forEach { it.move() }
                v.projectile_array.forEach { it.move() }
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
                TODO("블록 종류에 맞춰 이벤트 실행하는 코드")
            }
        }
        v.monster_array.forEach{    // 몬스터 클릭시
            val condition1: Boolean = touchX >= it.x && touchX <= it.x + it.width
            val condition2 : Boolean = touchY >= it.y && touchY <= it.y + it.height
            if(condition1 && condition2) {
                Toast.makeText(content, "$displayWidth , $displayHeight View 크기", Toast.LENGTH_SHORT).show()
                TODO("몬스터 정보 작게 표시")
            }
        }
        TODO("건물 또는 유닛 클릭시 정보 표시하는 코드")
    }


    // 투사체, 웨이브 딜레이 체크
    fun arrowCheck() {
        if (isTouch && arrowCounter >= arrowTime) {
            arrowStart()
            arrowCounter = 0
        } else {
            arrowCounter++
        }
        TODO("Arrow 클래스 생성, arrow 관련 함수를 분리 ")
    }
    fun arrowStart() {
        val arrawDrawable = v.resources.getDrawable(R.drawable.arrow, content.theme)
        v.projectile_array.add(
            Projectile(
                10,
                touchY.toInt(),
                137,
                17,
                arrawDrawable
            )
        )
        TODO("Arrow 클래스 생성, arrow 관련 함수를 분리 ")
    }

    // 투사체 수명 체크, 충돌 체크
    fun checkProjectile(arrayList: ArrayList<Projectile>) {
        var n: Int = 0
        while (n < arrayList.size) {
            if (arrayList[n].lifetime == 0) {
                arrayList.removeAt(n)
                n--
            }
            n++
        }
        TODO("Arrow 클래스 생성, arrow 관련 함수를 분리 ")
    }
    fun checkCollision(monster: ArrayList<Monster>, projectile: ArrayList<Projectile>) {
        var n: Int = 0
        var m: Int = 0
        while (n < monster.size) {
            while (m < projectile.size) {
                val condition1: Boolean =
                    monster[n].x < projectile[m].x + projectile[m].width && monster[n].x + monster[n].width > projectile[m].x
                val condition2: Boolean =
                    monster[n].y < projectile[m].y + projectile[m].height && monster[n].y + monster[n].height > projectile[m].y
                if (condition1 && condition2) {
                    makeEffect(monster[n].x+monster[n].width/2,monster[n].y+monster[n].height/2)
                    monster.removeAt(n)
                    projectile.removeAt(m)
                    m = 0
                    break
                }
                m++
            }
            m = 0
            n++
        }
        TODO("Arrow 클래스 생성, arrow 관련 함수를 분리 ")
    }

    // 애니메이션 이펙트 생성, 체크
    fun makeEffect(x:Int,y:Int){
        v.effect_array.add(
            Effect(
                x - 100,
                y - 100,
                200,
                200,
                effectBitmaps,
                30,
                10
            )
        )
        TODO("EffectManager 클래스 생성, Effect 관련 함수를 분리 ")
    }
    fun checkEffect(effect: ArrayList<Effect>) {
        var n: Int = 0
        while (n < effect.size) {
            effect[n].time += 1000/ping
            if (effect[n].time>=effect[n].lifetime) {
                effect.removeAt(n)
                n--
            }
            else {
                effect[n].bitmap_index = effect[n].time / effect[n].duration
            }
            n++
        }
        TODO("EffectManager 클래스 생성, Effect 관련 함수를 분리 ")
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
        TODO("보상을 계산하여, 데이터베이스에 적용, Dialog를 띄워서 결과창 출력, 버튼을 누르면 Loby 액티비티로 돌아감")
    }
    fun lose() {
        Toast.makeText(content,"승리!",Toast.LENGTH_SHORT).show()
        TODO("보상을 계산하여, 데이터베이스에 적용, Dialog를 띄워서 결과창 출력, 버튼을 누르면 Loby 액티비티로 돌아감")

    }
}