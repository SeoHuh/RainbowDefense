package com.test.rainbowDefense.battle

import android.content.Context
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.WaveEntity
import java.util.*

class WaveManager(
    val content : Context,
    val v : CanvasView,
    wave : WaveEntity,
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
    fun waveStart() {
        val normalDrawable = v.resources.getDrawable(R.drawable.monster_1, content.theme)
        val epicDrawable = v.resources.getDrawable(R.drawable.monster_1, content.theme)
        val uniqueDrawable = v.resources.getDrawable(R.drawable.monster_1,content.theme)
        val bossDrawable = v.resources.getDrawable(R.drawable.monster_1,content.theme)


        for(i in 1 until normalMonster) {
            v.monster_array.add(
                Monster(
                    rand(minX,maxX),
                    rand(minY,maxY),
                    1452 / 10,
                    1148 / 10,
                    normalDrawable
                )
            )
        }
        for(i in 1 until epicMonster) {
            v.monster_array.add(
                Monster(
                    rand(minX,maxX),
                    rand(minY,maxY),
                    1452 / 10,
                    1148 / 10,
                    epicDrawable
                )
            )
        }
        for(i in 1 until uniqueMonster) {
            v.monster_array.add(
                Monster(
                    rand(minX,maxX),
                    rand(minY,maxY),
                    1452 / 10,
                    1148 / 10,
                    uniqueDrawable
                )
            )
        }
        for(i in 1 until bossMonster) {
            v.monster_array.add(
                Monster(
                    rand(minX,maxX),
                    rand(minY,maxY),
                    1452 / 10,
                    1148 / 10,
                    bossDrawable
                )
            )
        }
        TODO("일반, 에픽, 유니크, 보스 등급의 몬스터 Drawable 구하고, 몬스터 데이터베이스와 연동하기")
    }
    val random = Random()
    fun rand(from : Int, to : Int) : Int {
        return random.nextInt(to - from) + from
    }

}