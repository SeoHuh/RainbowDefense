package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.WaveEntity

class EffectManager (
    val content : Context,
    val v : CanvasView,
    val ping : Int) {

    // 이펙트(애니메이션) 리소스
    val effectBitmaps = arrayListOf<Bitmap>();
    val effectId = arrayListOf(
        R.drawable.blueish_flame_0001,
        R.drawable.blueish_flame_0002,
        R.drawable.blueish_flame_0003,
        R.drawable.blueish_flame_0004,
        R.drawable.blueish_flame_0005,
        R.drawable.blueish_flame_0006,
        R.drawable.blueish_flame_0007,
        R.drawable.blueish_flame_0008,
        R.drawable.blueish_flame_0009,
        R.drawable.blueish_flame_0010,
        R.drawable.blueish_flame_0011,
        R.drawable.blueish_flame_0012,
        R.drawable.blueish_flame_0013,
        R.drawable.blueish_flame_0014,
        R.drawable.blueish_flame_0015,
        R.drawable.blueish_flame_0016,
        R.drawable.blueish_flame_0017,
        R.drawable.blueish_flame_0018,
        R.drawable.blueish_flame_0019,
        R.drawable.blueish_flame_0020,
        R.drawable.blueish_flame_0021,
        R.drawable.blueish_flame_0022,
        R.drawable.blueish_flame_0023,
        R.drawable.blueish_flame_0024,
        R.drawable.blueish_flame_0025,
        R.drawable.blueish_flame_0026,
        R.drawable.blueish_flame_0027,
        R.drawable.blueish_flame_0028,
        R.drawable.blueish_flame_0029,
        R.drawable.blueish_flame_0030
    )
    init {
        effectId.forEach {
            val bitmap = BitmapFactory.decodeResource(v.resources, it)
            effectBitmaps.add(Bitmap.createScaledBitmap(bitmap, 200, 200, false))
        }
    }

    // 애니메이션 이펙트 생성, 체크
    fun makeEffect(x:Int,y:Int){
        v.effect_array.add(
            Effect(
                x - 100,
                y - 100,
                200,
                200,
                effectBitmaps,
                30,
                10
            )
        )
    }
    fun checkEffect() {
        val effect = v.effect_array
        var n: Int = 0
        while (n < effect.size) {
            effect[n].time += 1000/ping
            if (effect[n].time>=effect[n].lifetime) {
                effect.removeAt(n)
                n--
            }
            else {
                effect[n].bitmap_index = effect[n].time / effect[n].duration
            }
            n++
        }
    }

}