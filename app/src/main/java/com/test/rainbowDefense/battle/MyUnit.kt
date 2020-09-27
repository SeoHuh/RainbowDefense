package com.test.rainbowDefense.battle

import android.graphics.drawable.Drawable
import kotlin.math.cos

// 유닛 클래스
// 아군 유닛으로 적군을 공격하고, 행동한다.

class MyUnit (
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    drawable: Drawable?)
    : Shape(x,y,width,height,drawable!!){

    override var lifetime = 1000

    var viewRange = 200
    var time = 0
    var attackDelay = ping
    var attackDamage = 0
    var attackSpeed = 0
    var attackRange = 0
    var attackType = 1
    var hp = 1
    var reward = 0
    var color = 0
    var projectileDrawable: Drawable? = null


    init {

    }
    override fun move(){
        super.move()
        time ++
    }
    fun stop() {
        vx = 0f
        vy = 0f
    }
}