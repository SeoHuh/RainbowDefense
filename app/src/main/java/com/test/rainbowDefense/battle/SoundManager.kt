package com.test.rainbowDefense.battle

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import com.test.rainbowDefense.R

class SoundManager(
    val content: Context,
    val v: CanvasView
) {

    inner class Sound(
        val id: Int,
        val string: String
    ) {

    }

    val soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
    val soundArray = arrayListOf<Sound>(
        Sound(soundPool.load(content, R.raw.metal_small_2, 1), "metal_small_2"),
        Sound(soundPool.load(content, R.raw.coin, 1), "coin"),
        Sound(soundPool.load(content, R.raw.swing, 1), "swing")

    )

    fun makeSound(string: String, amp:Float) {
        soundArray.forEach {
            if (it.string == string) {
                soundPool.play(it.id, amp, amp, 0, 0, 1f)
            }
        }
    }

}