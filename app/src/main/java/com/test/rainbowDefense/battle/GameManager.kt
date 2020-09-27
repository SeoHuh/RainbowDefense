package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
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
    private val statusHeight = 80

    // 핑 (초당 프레임 수)
    val ping = 120

    // 화면 좌표
    var touchStartX:Float = 0f
    var touchStartY:Float = 0f
    var touchX: Float = 100f
    var touchY: Float = 500f
    var isTouch: Boolean = false
    var isDrag: Boolean = false
    enum class ClickState(val state:Int){
        NORMAL(0),
        UNIT(1),
        SKILL(2),
        BUILDING(3),
        PAUSE(4)
    }
    var clickState = ClickState.NORMAL

    // 게임관련 각종 인스턴스
    val fontTypeface = Typeface.createFromAsset(content.assets,"jalnan.ttf")
    private val effectManager = EffectManager(content,v,ping)
    private val damageManager = DamageManager(content,v,fontTypeface,ping)
    private val soundManager = SoundManager(content,v)
    private val unitManager = UnitManager(content,v,effectManager,damageManager,ping,unitList)
    private val skillManager = SkillManager(content,v,displayWidth,battleHeight,effectManager,damageManager,ping,unitList)
    private val buildingManager = BuildingManager(content,v,effectManager,ping,unitList)
    private val monsterManager = MonsterManager(content,v,effectManager,soundManager,ping,monsterList)
    val arrowManager = ArrowManager(content,v,battleHeight,effectManager,damageManager,soundManager,ping)
    val blockMenu = BlockMenu(content,v,unitManager,skillManager,buildingManager,0,battleHeight,displayWidth,menuHeight,fontTypeface)
    val background = Background(displayWidth,battleHeight,statusHeight,v,content,fontTypeface)
    val waveManager = WaveManager(content,v,stage,wave,monsterManager,ping,displayWidth,battleHeight)

    private var listener: GameListener? = null
    fun setOnlistner(listener: GameListener) {
        this.listener = listener
    }

    interface GameListener {
        fun onPause()
        fun onEnd(isWin: Boolean, stage: Int, stageReward: Int, monsterReward: Int, hpPercent: Int)
    }

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
            clickState = ClickState.NORMAL
        }
    }
    inner class ThreadClass : Thread() {
        override fun run() {
            if(isRunning) { // 실행중인 경우
                arrowManager.checkArrow(isTouch,touchX,touchY)      // Arrow 생성을 체크
                arrowManager.checkProjectile()                      // Projectile 수명 체크
                arrowManager.checkCollision()                       // Projectile 충돌 체크
                effectManager.checkEffect()                         // Effect 수명 체크
                damageManager.checkLifetime()                       // Damage 수명 체크
                arrowManager.arrowMove()                            // Arrow 속도만큼 1프레임 이동
                unitManager.unitMove()                              // Unit 속도만큼 1프레임 이동
                monsterManager.monsterMove()                        // Monster 속도만큼 1프레임 이동
                damageManager.damageMove()                          // Damage 속도만큼 1프레임 이동
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
        touchStartX = x
        touchStartY = y
        touchX = x
        touchY = y
        v.cursor?.x = x.toInt()
        v.cursor?.y = y.toInt()
        when(clickState) {
            ClickState.SKILL ->
                v.skillShape?.let {
                    it.x = x.toInt() - it.width / 2
                    it.y = y.toInt() - it.height / 2
                }
            ClickState.NORMAL -> {
                isTouch = true
            }
            else -> {

            }
        }
    }
    private fun onMoveTouchEvent(x: Float, y: Float) {
        // 터치 중 이동할때 좌표 변경
        val dxTotal = Math.abs(x - touchStartX)
        val dx = Math.abs(x - touchX)
        val dy = Math.abs(y - touchY)

        if (dx >= CanvasView.TOLERANCE || dy >= CanvasView.TOLERANCE) {
            if (dxTotal >= CanvasView.DRAGTOLERANCE && touchStartY > displayHeight - menuHeight){   // 드래그 한계를 초과하면
                if(!isDrag) {       // 드래그 변수 참으로 설정
                    isDrag = true
                    if (clickState == ClickState.NORMAL) {
                        blockMenu.move((x - touchStartX).toInt())
                    }
                }
            }
            when(clickState) {
                ClickState.NORMAL ->
                    if (isDrag) {
                        blockMenu.move((x - touchX).toInt())
                    }
                ClickState.SKILL ->
                    v.skillShape?.let {
                        it.x = x.toInt() - it.width / 2
                        it.y = y.toInt() - it.height / 2
                    }
                else -> {

                }
            }
            touchX = x
            touchY = y
            v.cursor?.x = x.toInt()
            v.cursor?.y = y.toInt()
        }
    }
    private fun upTouchEvent() {

        when(clickState) {
            ClickState.SKILL -> {
                skillManager.endSkill(touchX.toInt(), touchY.toInt())
                background.removeShadow()
                clickState = ClickState.NORMAL
            }
            ClickState.BUILDING -> {
                background.removeShadow()
                clickState = ClickState.NORMAL
            }
            ClickState.NORMAL -> {
                if(!isDrag) {
                    onClickEvent()
                }
            }
            else -> {

            }
        }
        // 터치 끝날때, isTouch 변수 false 설정
        isTouch = false
        isDrag = false

    }
    private fun onClickEvent() { // 클릭 이벤트
        run{
            v.block_array.forEach {  // 블록 클릭시
                val condition1: Boolean = touchX >= it.x && touchX <= it.x + it.width
                val condition2: Boolean = touchY >= it.y && touchY <= it.y + it.height
                if (condition1 && condition2 && it.isClickable) {   // 클릭 위치와 버튼위치가 일치하면
                    when(it.type) {
                        "unit" -> it.onClick()
                        "building" -> {
                            if(it.onClick()){
                                clickState = ClickState.BUILDING
                                background.setShadow()
                            }
                        }
                        "skill" -> {
                            if(it.onClick()) {
                                clickState = ClickState.SKILL
                                background.setShadow()
                            }
                        }
                        "" -> it.onClick()
                    }
                    return@run
                }
            }
        }
        v.pause_button?.let {  // 포즈 버튼 클릭시
            val condition1: Boolean = touchX >= it.x && touchX <= it.x + it.width
            val condition2: Boolean = touchY >= it.y && touchY <= it.y + it.height
            if (condition1 && condition2) {   // 클릭 위치와 버튼위치가 일치하면
                listener?.onPause()
                clickState = ClickState.PAUSE
                isRunning = false
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
            clickState = ClickState.PAUSE
            win()
            isRunning = false
        }
        else if(v.status!!.hp<=0){      // 체력이 0 이하로 내려갔을 때
            clickState = ClickState.PAUSE
            lose()
            isRunning = false
        }
    }
    private fun win() {
        Toast.makeText(content,"승리!",Toast.LENGTH_SHORT).show()
        v.status?.let{
            listener?.onEnd(true,it.stage,it.stageReward,it.monsterReward,100*it.hp/it.hpMax)
        }
    }
    private fun lose() {
        Toast.makeText(content,"패배!",Toast.LENGTH_SHORT).show()
        v.status?.let{
            listener?.onEnd(false,it.stage,it.stageReward,it.monsterReward,100*it.hp/it.hpMax)
        }
    }
}