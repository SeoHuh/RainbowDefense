package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Typeface
import com.test.rainbowDefense.R

class DamageManager (
    val content : Context,
    val v : CanvasView,
    val font: Typeface,
    val ping : Int) {

    // 데미지 생성
     fun makeDamage(x: Float, y: Float, damage: Int, color: Int) {
        v.damage_array.add(
            Damage(
                x,
                y,
                damage.toString(),
                color,
                font,
                ping
            )
        )
    }

    // 데미지 수명 체크
    fun checkLifetime() {
        val arrayList = v.damage_array
        var n: Int = 0
        while (n < arrayList.size) {
            if (arrayList[n].lifetime == 0) {
                arrayList.removeAt(n)
                n--
            }
            n++
        }
    }

    fun damageMove() {
        v.damage_array.forEach { it.move() }
    }
}