package com.test.rainbowDefense.battle

import android.graphics.Color
import android.graphics.drawable.Drawable
import com.test.rainbowDefense.battle.Shape

// 투사체 객체
// Shpae 객체에서 공격력 추가

class Projectile(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    drawable: Drawable)
    : Shape(x,y,width,height,drawable){

    override var lifetime = 200
    override var vx = 1500f/ping
    var color: Int = Color.BLACK

    var attackDamage = 0
    init {

    }

}