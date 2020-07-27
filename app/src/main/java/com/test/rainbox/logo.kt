package com.test.rainbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.os.Handler
import android.view.View

class logo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        val fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeout = AnimationUtils.loadAnimation(this,R.anim.fade_out)

        val logoimg = findViewById<ImageView>(R.id.logo)

        //fade in
        logoimg.startAnimation(fadein)
        intent = Intent(this, intro::class.java)

        Handler().postDelayed({

            startActivity(intent)
            overridePendingTransition(R.anim.flash ,R.anim.fade_out)

        },3000)

        Handler().postDelayed({
            this.finish()
        }, 4000)


    }
}