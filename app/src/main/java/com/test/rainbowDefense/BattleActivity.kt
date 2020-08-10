package com.test.rainbowDefense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.test.rainbowDefense.battle.GameManager
import kotlinx.android.synthetic.main.activity_battle.*

class BattleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        setContentView(R.layout.activity_battle)
    }

    // 화면이 뜨고 난 뒤에 게임매니저 생성, 실행
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        var gameManager =
            GameManager(this, canvas)
        gameManager.run()
    }
}