package com.test.rainbowDefense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_loby.*

class loby : AppCompatActivity() {
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

        val intent_shop = Intent(this, shop::class.java)
        val intent_reinforce = Intent(this, reinforce::class.java)
        val intent_setting = Intent(this, setting::class.java)
        val intent_battle = Intent(this, battle::class.java)

        btn_loby_shop.setOnClickListener { view ->
            startActivity(intent_shop)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_loby_reinforce.setOnClickListener { view ->
            startActivity(intent_reinforce)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        btn_loby_setting.setOnClickListener { view ->
            startActivity(intent_setting)
            overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)
        }
        btn_loby_battle.setOnClickListener {
            startActivity(intent_battle)
        }
    }
}