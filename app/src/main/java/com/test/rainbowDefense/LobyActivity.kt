package com.test.rainbowDefense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.rainbowDefense.battle.GameManager
import com.test.rainbowDefense.database.StateEntity
import com.test.rainbowDefense.database.StateViewModel
import com.test.rainbowDefense.database.UnitEntity
import com.test.rainbowDefense.database.UnitViewModel
import kotlinx.android.synthetic.main.activity_battle.*
import kotlinx.android.synthetic.main.activity_loby.*

class LobyActivity : AppCompatActivity() {

    private lateinit var stateViewModel: StateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loby)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        val intent_shop = Intent(this, ShopActivity::class.java)
        val intent_reinforce = Intent(this, ReinforceActivity::class.java)
        val intent_battle = Intent(this, BattleActivity::class.java)

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
            startActivity(intent_battle)
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
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    fun showSetting() {
        val newFragment = SettingLobyDetailFragment()
        newFragment.show(supportFragmentManager, "mine")
        newFragment.setOnlistner(object :
            SettingLobyDetailFragment.NoticeDialogListener {
            override fun onExitClick(dialog: DialogFragment) {
            }

            override fun onMuteClick(dialog: DialogFragment) {
            }

            override fun onVibrateClick(dialog: DialogFragment) {

            }

            override fun onCouponClick(dialog: DialogFragment) {
            }
        })
    }
}