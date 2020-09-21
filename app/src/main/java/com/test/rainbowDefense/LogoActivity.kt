package com.test.rainbowDefense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.os.Handler
import android.view.View

class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)
        setFullScreen()

        val fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeout = AnimationUtils.loadAnimation(this,R.anim.fade_out)

        val logoimg = findViewById<ImageView>(R.id.logo)

        //fade in
        logoimg.startAnimation(fadein)
        intent = Intent(this, IntroActivity::class.java)

        Handler().postDelayed({
            overridePendingTransition(R.anim.flash ,R.anim.fade_out)
            startActivity(intent)
            this.finish()
        },3000)
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