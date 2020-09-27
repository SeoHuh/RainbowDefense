package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Color
import com.test.rainbowDefense.R

class ManaManager (
    val v: CanvasView,
    ping : Int,
    cloudRecovery : Int,
    manaRecovery : Int,
    val manaMax : Int) {

    // 마나 딜레이타임, 카운터
    var manaDelayTime: Int = (2400f / manaRecovery).toInt()
    var manaCounter: Int = 0

    // 마나 회복
    fun checkMana() {
        if (manaCounter >= manaDelayTime) {
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
    // 구름 딜레이타임, 카운터
    var cloudDelayTime: Int = (2400f / cloudRecovery).toInt()
    var cloudCounter: Int = 0
    var cloudMax: Int = 1000

    // 구름 회복
    fun checkCloud() {
        if (cloudCounter >= cloudDelayTime) {
            recoverCloud(1)
            cloudCounter = 0
        } else {
            cloudCounter++
        }
    }
    fun recoverCloud(num: Int) {
        if(v.status!!.cloud < cloudMax){
            v.status!!.apply{
                cloud += num
            }
        }
    }
}