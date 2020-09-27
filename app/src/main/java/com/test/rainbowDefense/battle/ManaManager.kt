package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Color
import com.test.rainbowDefense.R

class ManaManager (
    val v: CanvasView,
    ping : Int) {

    // 마나 딜레이타임, 카운터
    var delayTime: Int = (0.2 * ping).toInt()
    var manaCounter: Int = 0
    var manaMax: Int = 100

    // 마나 회복
    fun checkMana() {
        if (manaCounter >= delayTime) {
            recoverMana(1)
            manaCounter = 0
        } else {
            manaCounter++
        }
    }
    fun recoverMana(num: Int) {
        if(v.status!!.mana < manaMax){
            v.status!!.apply{
                mana += num
            }
        }
    }
}