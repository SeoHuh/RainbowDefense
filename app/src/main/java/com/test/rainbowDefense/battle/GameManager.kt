package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.view.MotionEvent
import android.widget.Toast
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.MonsterEntity
import com.test.rainbowDefense.database.StageEntity
import com.test.rainbowDefense.database.UnitEntity
import com.test.rainbowDefense.database.WaveEntity

// 게임 매니저 클래스
// 게임 내부의 모든 요소를 종합해서 관리한다.

class GameManager(
    val content: Context,
    val v: CanvasView,
    stage: StageEntity,
    wave: WaveEntity,
    monsterList: List<MonsterEntity>,
    unitList: List<UnitEntity>) {

    // 실행중?
    var isRunning = false

    // 핸들러 쓰레드
    var handler: Handler? = Handler()
    var thread = ThreadClass()

    // 화면 크기 고정
    private val displayWidth = 1920
    private val displayHeight = 1080

    // 전투화면 높이
    private val battleHeight = 840

    // 메뉴(블록)화면 높이
    private val menuHeight = 240

    // 상태창 높이
    private val statusHeight = 120

    // 핑 (초당 프레임 수)
    val ping = 120

    // 화면 좌표
    var touchX: Float = 100f
    var touchY: Float = 500f
    var isTouch: Boolean = false
    var isTouchSkill: Boolean = false
    var isTouchBuilding: Boolean = false

    // 게임관련 각종 인스턴스
    private val effectManager = EffectManager(content,v,ping)
    private val unitManager = UnitManager(content,v,effectManager,ping,unitList)
    private val skillManager = SkillManager(content,v,effectManager,ping,unitList)
    private val buildingManager = BuildingManager(content,v,effectManager,ping,unitList)
    private val monsterManager = MonsterManager(content,v,effectManager,ping,monsterList)
    val arrowManager = ArrowManager(content,v,effectManager,ping)
    val blockMenu = BlockMenu(content,v,unitManager,skillManager,buildingManager,0,battleHeight,displayWidth,menuHeight)
    val waveManager = WaveManager(content,v,wave,monsterManager,ping,displayWidth,battleHeight)
    val background = Background(displayWidth,battleHeight,statusHeight,v,content)


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
            if(isRunning) { // 실행중인 경우
                arrowManager.checkArrow(isTouch,touchX,touchY)      // Arrow 생성을 체크
                arrowManager.checkProjectile()                      // Projectile 수명 체크
                arrowManager.checkCollision()                       // Projectile 충돌 체크
                effectManager.checkEffect()                         // Effect 수명 체크
                arrowManager.arrowMove()                            // Arrow 속도만큼 1프레임 이동
                unitManager.unitMove()                              // Unit 속도만큼 1프레임 이동
                monsterManager.monsterMove()                        // Monster 속도만큼 1프레임 이동
                unitManager.checkDead()                             // Unit 사망 체크
                monsterManager.checkDead()                          // Monster 사망 체크
                monsterManager.checkAttack()                        // Monster 공격 체크
                waveManager.waveCheck()                             // Wave 생성 체크
                v.invalidate()                                      // View 그리기
                winCheck()                                          // 승리,패배 체크

                handler?.postDelayed(this, 1000 / ping.toLong())    // 다음 프레임
            }
        }
    }

    // 터치 이벤트 관련
    private fun onStartTouchEvent(x: Float, y: Float) {
        // 터치 시작시 좌표 변경
        // 커서의 시작 좌표 변경
        // isTouch 변수 true 설정
        // 터치한 곳의 이벤트 실행
        touchX = x
        touchY = y
        v.cursor?.x = x.toInt()
        v.cursor?.y = y.toInt()
        isTouch = true
        checkTouch()
        v.invalidate()
    }
    private fun onMoveTouchEvent(x: Float, y: Float) {
        // 터치 중 이동할때 좌표 변경
        val dx = Math.abs(x - touchX)
        val dy = Math.abs(y - touchY)
        if (dx >= CanvasView.TOLERANCE || dy >= CanvasView.TOLERANCE) {
            touchX = x
            touchY = y
            v.cursor?.x = x.toInt()
            v.cursor?.y = y.toInt()
        }
        if (isTouchSkill){
            v.skillShape!!.x = touchX.toInt() - v.skillShape!!.width/2
            v.skillShape!!.y = touchY.toInt() - v.skillShape!!.height/2
        }
        v.invalidate()
    }
    private fun upTouchEvent() {
        // 터치 끝날때, isTouch 변수 false 설정
        isTouch = false
        if (isTouchSkill){
            v.skillShape = null
            skillManager.useSkill(touchX.toInt(),touchY.toInt())
            isTouchSkill = false
        }
        if (isTouchBuilding) {

            isTouchBuilding = false
        }
    }
    private fun checkTouch() { // 터치한 부분에 맞는 이벤트 실행(블록 클릭, 유닛 클릭, 적유닛 클릭 등등)
        run{
            v.block_array.forEach {  // 블록 클릭시
                val condition1: Boolean = touchX >= it.x && touchX <= it.x + it.width
                val condition2: Boolean = touchY >= it.y && touchY <= it.y + it.height
                if (condition1 && condition2 && it.isClickable) {   // 클릭 위치와 버튼위치가 일치하면
                    when(it.type) {
                        "unit" -> it.onClick()
                        "building" -> {
                            if(it.onClick()){
                                isTouchBuilding = true

                            }
                        }
                        "skill" -> {
                            if(it.onClick()) {
                                isTouchSkill = true
                                v.skillShape!!.x = touchX.toInt() - v.skillShape!!.width/2
                                v.skillShape!!.y = touchY.toInt() - v.skillShape!!.height/2
                            }
                        }
                    }
                    return@run
                }
            }
        }

        v.monster_array.forEach{    // 몬스터 클릭시
            val condition1: Boolean = touchX >= it.x && touchX <= it.x + it.width
            val condition2 : Boolean = touchY >= it.y && touchY <= it.y + it.height
            if(condition1 && condition2) {
//                TODO("몬스터 정보 작게 표시")
            }
        }
//        TODO("건물 또는 유닛 클릭시 정보 표시하는 코드")
    }

    // 승리, 패배 조건 확인
    private fun winCheck(){
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
    private fun win() {
        Toast.makeText(content,"승리!",Toast.LENGTH_SHORT).show()
//        TODO("보상을 계산하여, 데이터베이스에 적용, Dialog를 띄워서 결과창 출력, 버튼을 누르면 Loby 액티비티로 돌아감")
    }
    private fun lose() {
        Toast.makeText(content,"패배!",Toast.LENGTH_SHORT).show()
//        TODO("보상을 계산하여, 데이터베이스에 적용, Dialog를 띄워서 결과창 출력, 버튼을 누르면 Loby 액티비티로 돌아감")

    }
}