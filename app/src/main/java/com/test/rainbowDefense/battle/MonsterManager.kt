package com.test.rainbowDefense.battle

import android.content.Context
import android.util.Log
import com.test.rainbowDefense.database.MonsterEntity
import java.util.*
import kotlin.math.atan2
import kotlin.math.sqrt

// 몬스터 객체 매니저
// 몬스터 데이터베이스를 가지고 와서 상황에 맞는 몬스터를 찾거나 randMonster(rank),
// 게임 내의 몬스터 이동 moveMonster(),
// 몬스터의 사망 처리 checkDead(),
// 몬스터의 공격 처리 checkAttack(),
// 몬스터의 생성 처리 makeMonster(x,y,rank)
// 몬스터의 등급은 1 노말, 2 에픽, 3 유니크, 4 보스


class MonsterManager (
    val content : Context,
    val v : CanvasView,
    val effectManager: EffectManager,
    val soundManager: SoundManager,
    ping : Int,
    val monsterList: List<MonsterEntity>) {

    init{
        monsterList.forEach{
            val resId: Int =
                v.resources.getIdentifier(it.resourceId, "drawable", "com.test.rainbowDefense")
            it.drawable = v.resources.getDrawable(resId,content.theme)
        }
    }



    fun randMonster(rank:Int):MonsterEntity {
        val list = monsterList.filter{it.rank == rank}
        var monster :MonsterEntity = monsterList[0]
        if(list.isNotEmpty()) {
            monster = list[rand(0, list.size)]
        }
        return monster
    }

    fun monsterMove() {
        v.monster_array.forEach {
            monsterAI(it)
            it.move() }
    }

    fun checkDead() {
        val arrayList = v.monster_array
        var n: Int = 0
        while (n < arrayList.size) {
            if (arrayList[n].hp <= 0) {
                soundManager.makeSound("coin",0.6f)
                v.status!!.apply{
                    monsterReward += arrayList[n].reward
                    cloud += arrayList[n].reward
                }
                arrayList.removeAt(n)
                n--
            }
            n++
        }
    }
    fun checkAttack() {
        val arrayList = v.monster_array
        var n: Int = 0
        while(n < arrayList.size) {
            if(arrayList[n].x+arrayList[n].width<=0){
                v.status?.apply{
                    hp -= arrayList[n].attackDamage/2
                }
                arrayList.removeAt(n)
                n--
            }
            n++
        }
    }

    fun makeMonster(hpMag:Int, damageMag:Int, x: Int,y: Int,rank: Int) {
        val monster = randMonster(rank)
        Log.d("디버그", "makeMonster: ${monster.name}")
        v.monster_array.add(
            Monster(
                x,
                y,
                monster.width,
                monster.height,
                monster.drawable
            ).apply{
                this.setVelocity(monster.moveSpeed/ping.toFloat(),Math.PI.toFloat())
                attackDamage = monster.attackDamage * damageMag/100
                attackSpeed = monster.attackSpeed
                attackRange = monster.attackRange
                hp = monster.hp * hpMag/100
                reward = monster.reward
            }
        )
    }

    // 유닛의 AI, 가까운 몬스터 찾기, 몬스터와의 거리, 각도 측정
    private fun monsterAI(monster: Monster) {
        if(v.unit_array.isNotEmpty()) {      // 유닛이 있으면
            val closeUnit = findCloseUnit(monster)
            val closeDistance = findDistance(monster,closeUnit)
            val closeAngle = findAngle(monster,closeUnit)

            when {
                closeDistance<=monster.attackRange -> {    // 공격 범위 내부일 경우 공격
                    monster.stop()
                    attack(monster,closeUnit)
                }
                closeDistance<=monster.viewRange -> {      // 시야 범위 내부일 경우 이동
                    monster.setVelocity(monster.speed, closeAngle.toFloat())
                }
                else -> {       // 그 외 범위일 경우 전진
                    monster.setVelocity(monster.speed,Math.PI.toFloat())
                }
            }
        }
        else{       // 유닛이 없으면 전진
            monster.setVelocity(monster.speed,Math.PI.toFloat())
        }
    }
    private fun attack(monster: Monster,unit: MyUnit){
        unit.apply{
            hp -= monster.attackDamage
        }
    }
    private fun findCloseUnit(monster: Monster): MyUnit{
        var closeUnit = v.unit_array[0]
        var closeDistance = findDistance(monster,closeUnit)

        v.unit_array.forEach {unit ->
            val distance = findDistance(monster,unit)
            if(distance <= closeDistance){
                closeUnit = unit
                closeDistance = distance
            }
        }
        return closeUnit
    }
    private fun findDistance(monster: Monster,unit: MyUnit): Double{
        val distanceX:Double = ((unit.x + unit.width / 2) - (monster.x + monster.width / 2)).toDouble()
        val distanceY:Double = ((unit.y + unit.height / 2) - (monster.y + monster.height / 2)).toDouble()
        val distance = sqrt(Math.pow(distanceX,2.0) + Math.pow(distanceY,2.0))
        return distance
    }
    private fun findAngle(monster: Monster,unit: MyUnit): Double{
        val distanceX:Double = ((unit.x + unit.width / 2) - (monster.x + monster.width / 2)).toDouble()
        val distanceY:Double = ((unit.y + unit.height / 2) - (monster.y + monster.height / 2)).toDouble()
        val angle = atan2(distanceY,distanceX)
        return angle
    }


    private val random = Random()
    private fun rand(from : Int, to : Int) : Int {
        return random.nextInt(to - from) + from
    }
}