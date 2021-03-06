package com.test.rainbowDefense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.rainbowDefense.database.StateEntity
import com.test.rainbowDefense.database.StateViewModel
import com.test.rainbowDefense.fragment.LobySettingFragment
import kotlinx.android.synthetic.main.activity_loby.*

class LobyActivity : AppCompatActivity() {

    private lateinit var stateViewModel: StateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loby)
        setFullScreen()

        val intent_shop = Intent(this, ShopActivity::class.java)
        val intent_reinforce = Intent(this, ReinforceActivity::class.java)
        val intent_battlerounds = Intent(this, RoundsActivity::class.java)

        btn_loby_shop.setOnClickListener { view ->
            startActivity(intent_shop)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_loby_reinforce.setOnClickListener { view ->
            startActivity(intent_reinforce)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        btn_loby_setting.setOnClickListener { view ->
            showSetting()
        }
        btn_loby_battle.setOnClickListener {
            startActivity(intent_battlerounds)
            overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top)
        }
        stateViewModel = ViewModelProvider(this)[StateViewModel::class.java]
        stateViewModel.state.observe(this, Observer { state ->
            state?.let {
                stage_text.text = it[0].stage.toString()
                gold_text.text = it[0].gold.toString()
            }
            Log.d("디버깅", "State 변경확인")
        })
        gold_text.setOnClickListener {
            val state: StateEntity = stateViewModel.state.value!!.get(0)
            stateViewModel.update(state.apply { gold+=10 })
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setFullScreen()
    }

    fun showSetting() {
        val state = stateViewModel.state.value!!.get(0)
        val newFragment =
            LobySettingFragment(state)
        newFragment.show(supportFragmentManager, "mine")
        newFragment.setOnlistner(object :
            LobySettingFragment.NoticeDialogListener {
            override fun onExitClick(dialog: DialogFragment) {
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

            override fun onCouponClick(dialog: DialogFragment) {
            }

            override fun musicProgressChanged(view: View, i: Int) {
                stateViewModel.update(state.apply { musicVolume = i })
            }

            override fun effectProgressChanged(view: View, i: Int) {
                stateViewModel.update(state.apply { effectVolume = i })
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
    }
}