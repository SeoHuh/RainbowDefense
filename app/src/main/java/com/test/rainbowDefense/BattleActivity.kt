package com.test.rainbowDefense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.rainbowDefense.battle.GameManager
import com.test.rainbowDefense.database.*
import com.test.rainbowDefense.fragment.BattleResultFragment
import com.test.rainbowDefense.fragment.BattleSettingFragment
import kotlinx.android.synthetic.main.activity_battle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BattleActivity : AppCompatActivity() {

    private lateinit var stateViewModel: StateViewModel
    private lateinit var gameManager: GameManager
    private lateinit var stage : StageEntity
    private lateinit var wave: WaveEntity
    private lateinit var monsterList: List<MonsterEntity>
    private lateinit var unitList: List<UnitEntity>
    private var isCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()

        val stageNum = intent.getIntExtra("Round",1)
        val db = MyRoomDatabase.getDatabase(this)

        CoroutineScope(Dispatchers.IO).launch {
            stage = db.stageDao().get()[stageNum - 1]
            wave = db.waveDao().get()[stage.waveDifficulty - 1]
            monsterList = db.monsterDao().get()
            unitList = db.unitDao().getHaveList()
        }

        stateViewModel = ViewModelProvider(this)[StateViewModel::class.java]
        stateViewModel.state.observe(this, Observer { state ->
            state?.let {

            }
            Log.d("디버깅", "State 변경확인")
        })
    }

    // 화면이 뜨고 난 뒤에 게임매니저 생성, 실행
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setFullScreen()
        if(!isCreated) {
            gameManager = GameManager(this, canvas, stage, wave, monsterList, unitList)
            gameManager.setOnlistner(object:
            GameManager.GameListener{
                override fun onPause() {
                    showSetting()
                }

                override fun onEnd(
                    isWin: Boolean,
                    stage: Int,
                    stageReward: Int,
                    monsterReward: Int,
                    hpPercent: Int
                ) {
                    val state = stateViewModel.state.value!!.get(0)
                    var total = 0
                    if(isWin) {
                        total = stageReward * (100 + hpPercent) / 100 + monsterReward
                    }
                    else{
                        total = monsterReward
                    }
                    stateViewModel.update(state.apply { gold += total })
                    showReward(isWin, stage, stageReward, monsterReward, hpPercent)
                }
            })
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

    fun showSetting() {
        val state = stateViewModel.state.value!!.get(0)
        val newFragment =
            BattleSettingFragment(state)
        newFragment.show(supportFragmentManager, "mine")
        newFragment.setOnlistner(object :
            BattleSettingFragment.NoticeDialogListener {
            override fun onExitClick(dialog: DialogFragment) {
                this@BattleActivity.finish()
            }

            override fun onMuteClick(view: View) {
                stateViewModel.update(state.apply {
                    if (this.muteState == 0) {
                        this.muteState = 1
                        view.setBackgroundResource(R.drawable.mute)
                    } else {
                        this.muteState = 0
                        view.setBackgroundResource(R.drawable.notmute)
                    }
                })
            }

            override fun onVibrateClick(view: View) {
                stateViewModel.update(state.apply {
                    if (this.vibrateState == 0) {
                        this.vibrateState = 1
                        view.setBackgroundResource(R.drawable.vibration)
                    } else {
                        this.vibrateState = 0
                        view.setBackgroundResource(R.drawable.novibration)
                    }
                })
            }

            override fun onResumeClick(dialog: DialogFragment) {
                gameManager.run()
            }
            override fun musicProgressChanged(view: View, i: Int) {
                stateViewModel.update(state.apply { musicVolume = i })
            }

            override fun effectProgressChanged(view: View, i: Int) {
                stateViewModel.update(state.apply { effectVolume = i })
            }
        })
    }
    fun showReward(isWin: Boolean, stage : Int, stageReward : Int, monsterReward : Int, hpPercent : Int) {
        val state = stateViewModel.state.value!!.get(0)
        val newFragment =
            BattleResultFragment(state,isWin,stage,stageReward,monsterReward,hpPercent)
        newFragment.show(supportFragmentManager, "mine")
        newFragment.setOnlistner(object :
            BattleResultFragment.NoticeDialogListener {
            override fun onExitClick(dialog: DialogFragment) {
                this@BattleActivity.finish()
            }
        })
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