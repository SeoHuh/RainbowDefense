package com.test.rainbowDefense.battle

import android.content.Context
import android.util.Log
import com.test.rainbowDefense.database.MonsterEntity
import com.test.rainbowDefense.database.UnitEntity
import java.util.*
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.sqrt

class UnitManager (
    val content : Context,
    val v : CanvasView,
    val effectManager: EffectManager,
    ping : Int,
    val unitList: List<UnitEntity>) {

    init {
        unitList.forEach {
            val resId: Int =
                v.resources.getIdentifier(it.resourceId, "drawable", "com.test.rainbowDefense")
            it.drawable = v.resources.getDrawable(resId, content.theme)
        }
    }

    fun getUnit() = unitList.filter{it.type == "unit"}
    fun getSkill() = unitList.filter{it.type == "skill"}
    fun getBuilding() = unitList.filter{it.type == "building"}

    fun unitMove() {
        v.unit_array.forEach { unit ->
            if(v.monster_array.isNotEmpty()) {
                var closeMonster = v.monster_array[0]
                var closeDistance : Double = 1.0
                var closeAngle : Double = 0.0

                v.monster_array.forEach {monster ->
                    val closeDistanceX:Double = ((unit.x + unit.width / 2) - (closeMonster.x + closeMonster.width / 2)).toDouble()
                    val closeDistanceY:Double = ((unit.y + unit.height / 2) - (closeMonster.y + closeMonster.height / 2)).toDouble()
                    closeDistance = sqrt(Math.pow(closeDistanceX,2.0) + Math.pow(closeDistanceY,2.0))
                    closeAngle = atan2(-closeDistanceY,-closeDistanceX)

                    val monsterDistanceX:Double = ((unit.x + unit.width / 2) - (monster.x + monster.width / 2)).toDouble()
                    val monsterDistanceY:Double = ((unit.y + unit.height / 2) - (monster.y + monster.height / 2)).toDouble()
                    val monsterDistance = sqrt(Math.pow(monsterDistanceX,2.0) + Math.pow(monsterDistanceY,2.0))

                    if(monsterDistance<= closeDistance){
                        closeMonster = monster
                    }
                }
                when {
                    closeDistance<=unit.attackRange -> {    // 공격 범위 내부
                        unit.stop()
                        attack(unit,closeMonster)
                    }
                    closeDistance<=unit.viewRange -> {      // 시야 범위 내부
                        unit.setVelocity(unit.speed, closeAngle.toFloat())
                    }
                    else -> {       // 그 외 범위
                        unit.stop()
                    }
                }
            }
            else{
                unit.stop()
            }
            unit.move()
        }
    }
    fun checkDead() {
        val arrayList = v.unit_array
        var n: Int = 0
        while (n < arrayList.size) {
            if (arrayList[n].hp <= 0) {
                arrayList.removeAt(n)
                n--
            }
            n++
        }
    }
    fun attack(unit:MyUnit, monster: Monster) {
        if(unit.time>=unit.attackDelay){
            monster.hp -= unit.attackDamage
            unit.time = 0
        }
    }

    fun makeUnit(x: Int, y: Int, unit:UnitEntity) {
        Log.d("디버그", "makeUnit: ${unit.name}")
        v.unit_array.add(
            MyUnit(
                x,
                y,
                unit.width,
                unit.height,
                unit.drawable
            ).apply {
                setVelocity(unit.moveSpeed / ping.toFloat(),0f)
                attackDamage = unit.attackDamage
                attackSpeed = unit.attackSpeed
                attackRange = unit.attackRange
                hp = unit.hp
            }
        )
    }

    private val random = Random()
    private fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }
}