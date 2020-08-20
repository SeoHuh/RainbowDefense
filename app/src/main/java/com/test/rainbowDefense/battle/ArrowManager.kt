package com.test.rainbowDefense.battle

import android.content.Context
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.WaveEntity

class ArrowManager (
    val content : Context,
    val v : CanvasView,
    val effectManager: EffectManager,
    ping : Int) {

    // 화살 딜레이타임, 카운터 (추후 Arrow 클래스로 옮길 예정)
    var delayTime: Int = (0.1 * ping).toInt()
    var arrowCounter: Int = 0

    // 투사체, 웨이브 딜레이 체크
    fun arrowCheck(isTouch:Boolean,x:Float,y:Float) {
        if (isTouch && arrowCounter >= delayTime) {
            arrowStart(x,y)
            arrowCounter = 0
        } else {
            arrowCounter++
        }
    }
    private fun arrowStart(x:Float,y:Float) {
        val arrawDrawable = v.resources.getDrawable(R.drawable.arrow, content.theme)
        v.projectile_array.add(
            Projectile(
                10,
                y.toInt(),
                137,
                17,
                arrawDrawable
            )
        )
    }

    // 투사체 수명 체크, 충돌 체크
    fun checkProjectile() {
        val arrayList = v.projectile_array
        var n: Int = 0
        while (n < arrayList.size) {
            if (arrayList[n].lifetime == 0) {
                arrayList.removeAt(n)
                n--
            }
            n++
        }
    }
    fun checkCollision() {
        val monster = v.monster_array
        val projectile = v.projectile_array
        var n: Int = 0
        var m: Int = 0
        while (n < monster.size) {
            while (m < projectile.size) {
                val condition1: Boolean =
                    monster[n].x < projectile[m].x + projectile[m].width && monster[n].x + monster[n].width > projectile[m].x
                val condition2: Boolean =
                    monster[n].y < projectile[m].y + projectile[m].height && monster[n].y + monster[n].height > projectile[m].y
                if (condition1 && condition2) {
                    effectManager.makeEffect(monster[n].x+monster[n].width/2,monster[n].y+monster[n].height/2)
                    monster.removeAt(n)
                    projectile.removeAt(m)
                    m = 0
                    break
                }
                m++
            }
            m = 0
            n++
        }
    }
    fun arrowMove() {
        v.projectile_array.forEach {it.move()}
    }



}