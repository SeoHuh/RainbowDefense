package com.test.rainbowDefense

import android.graphics.drawable.Drawable

class Projectile(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    drawable: Drawable)
    :Shape(x,y,width,height,drawable){

    override var lifetime = 200
    override var vx = 1500/ping

    init {

    }

}