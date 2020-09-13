package com.test.rainbowDefense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.test.rainbowDefense.battle.GameManager
import com.test.rainbowDefense.database.*
import kotlinx.android.synthetic.main.activity_battle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BattleActivity : AppCompatActivity() {

    private lateinit var gameManager: GameManager
    private lateinit var stage : StageEntity
    private lateinit var wave: WaveEntity
    private lateinit var monsterList: List<MonsterEntity>
    private lateinit var unitList: List<UnitEntity>
    private var isCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()

        val stageNum = 1
        val db = MyRoomDatabase.getDatabase(this)

        CoroutineScope(Dispatchers.IO).launch {
            stage = db.stageDao().get()[stageNum - 1]
           // wave = db.waveDao().get()[stage.waveDifficulty - 1]
            wave = db.waveDao().get()[2]
            monsterList = db.monsterDao().get()
            unitList = db.unitDao().getHaveList()
        }
    }

    // 화면이 뜨고 난 뒤에 게임매니저 생성, 실행
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setFullScreen()
        if(!isCreated) {
            gameManager = GameManager(this, canvas, stage, wave, monsterList, unitList)
            isCreated = true
        }
        if(hasFocus) {
            gameManager.run()
        }
        else{
            gameManager.isRunning = false
        }
    }

    override fun onPause() {
        super.onPause()
        gameManager.isRunning = false
    }

    fun setFullScreen() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        setContentView(R.layout.activity_battle)
    }
}