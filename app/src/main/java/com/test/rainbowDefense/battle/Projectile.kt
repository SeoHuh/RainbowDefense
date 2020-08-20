package com.test.rainbowDefense.battle

import android.graphics.drawable.Drawable
import com.test.rainbowDefense.battle.Shape

class Projectile(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    drawable: Drawable)
    : Shape(x,y,width,height,drawable){

    override var lifetime = 200
    override var vx = 1500/ping

    var attackDamage = 0
    init {

    }

}