package com.test.rainbowDefense.battle

import android.graphics.drawable.Drawable

class Monster (
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    drawable: Drawable)
    : Shape(x,y,width,height,drawable){

    override var lifetime = 1000
    override var vx = -700/ping

    init {

    }
}