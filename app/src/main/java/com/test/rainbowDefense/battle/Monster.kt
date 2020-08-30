package com.test.rainbowDefense.battle

import android.graphics.drawable.Drawable

// 몬스터 객체 정의
// Shape 객체를 상속받으며
// 공격력, 공격속도, 공격범위, 체력, 보상 등이 있다.

class Monster (
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    drawable: Drawable?)
    : Shape(x,y,width,height,drawable!!){

    override var lifetime = 1000
    override var vx = -700f/ping

    var viewRange = 1000
    var attackDamage = 0
    var attackSpeed = 0
    var attackRange = 0
    var hp = 1
    var reward = 0


    init {

    }

    fun stop() {
        vx = 0f
        vy = 0f
    }

}