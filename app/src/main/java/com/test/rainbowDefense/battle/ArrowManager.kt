package com.test.rainbowDefense.battle

import android.content.Context
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.WaveEntity

// 화살 매니저
// 투사체중 하나인 화살을 관리하며
// 화살의 생성, 이동, 수명체크, 충돌체크 등을 모두 관리한다.

class ArrowManager (
    val content : Context,
    val v : CanvasView,
    val effectManager: EffectManager,
    val soundManager: SoundManager,
    ping : Int) {

    // 화살 딜레이타임, 카운터
    var delayTime: Int = (0.1 * ping).toInt()
    var arrowCounter: Int = 0
    var attackDamage = 30


    // 투사체, 웨이브 딜레이 체크
    fun checkArrow(isTouch:Boolean,x:Float,y:Float) {
        if (isTouch && arrowCounter >= delayTime) {
            makeArrow(x,y)
            arrowCounter = 0
        } else {
            arrowCounter++
        }
    }
    private fun makeArrow(x:Float,y:Float) {
        val arrawDrawable = v.resources.getDrawable(R.drawable.arrow, content.theme)
        soundManager.makeSound("swing",1f)
        v.projectile_array.add(
            Projectile(
                10,
                y.toInt(),
                137,
                17,
                arrawDrawable
            ).apply{
                attackDamage = this@ArrowManager.attackDamage
            }
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
        monster.forEach{
            while (n < projectile.size) {
                val condition1: Boolean =
                    it.x < projectile[n].x + projectile[n].width && it.x + it.width > projectile[n].x
                val condition2: Boolean =
                    it.y < projectile[n].y + projectile[n].height && it.y + it.height > projectile[n].y
                if (condition1 && condition2) {
                    effectManager.makeEffect(it.x+it.width/2,it.y+it.height/2)
                    it.hp -= projectile[n].attackDamage // 몬스터 체력 감소
                    soundManager.makeSound("metal_small_2",0.5f)
                    projectile.removeAt(n)
                    n--
                }
                n++
            }
            n = 0
        }
    }
    fun arrowMove() {
        v.projectile_array.forEach {it.move()}
    }



}