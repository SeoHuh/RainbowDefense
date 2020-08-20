package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.drawable.Drawable
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.WaveEntity
import java.util.*

class WaveManager(
    val content : Context,
    val v : CanvasView,
    wave : WaveEntity,
    private val monsterManager: MonsterManager,
    ping : Int,
    displayWidth : Int,
    battleHeight : Int) {

    val minX = displayWidth
    val maxX = displayWidth * 2
    val minY = 80
    val maxY = battleHeight - 180

    // 처음 웨이브 대기시간
    var waveDelayTime = 2 * ping

    // 다음 웨이브 대기시간
    val delayTime = wave.delayTime * ping

    // 몬스터 숫자
    val bossMonster = wave.bossNumber
    val normalMonster = wave.normalNumber
    val epicMonster = wave.epicNumber
    val uniqueMonster = wave.uniqueNumber
    var Counter = 0

    fun waveCheck() {
        when {
            v.status?.wave == v.status?.waveMax -> { // 모든 웨이브 종료시
                // 아무것도 하지 않는다.
            }
            Counter >= waveDelayTime -> {   // 웨이브 시간이 다 되었을경우
                waveStart()
                Counter = 0
                waveDelayTime = delayTime
                v.status!!.wave +=1
            }
            else -> {
                Counter++
            }
        }
    }
    private fun waveStart() {

        for(i in 1 until normalMonster) {
            monsterManager.makeMonster(rand(minX,maxX),rand(minY,maxY),1)
        }
        for(i in 1 until epicMonster) {
            monsterManager.makeMonster(rand(minX,maxX),rand(minY,maxY),2)
        }
        for(i in 1 until uniqueMonster) {
            monsterManager.makeMonster(rand(minX,maxX),rand(minY,maxY),3)
        }
        for(i in 1 until bossMonster) {
            monsterManager.makeMonster(rand(minX,maxX),rand(minY,maxY),4)
        }
//        TODO("일반, 에픽, 유니크, 보스 등급의 몬스터 Drawable 구하고, 몬스터 데이터베이스와 연동하기")
    }

    private val random = Random()
    private fun rand(from : Int, to : Int) : Int {
        return random.nextInt(to - from) + from
    }

}