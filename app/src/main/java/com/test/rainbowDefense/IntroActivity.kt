package com.test.rainbowDefense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        intent = Intent(this, LobyActivity::class.java )

        btn_play.setOnClickListener {view ->
            startActivity(intent)

        }
    }
}