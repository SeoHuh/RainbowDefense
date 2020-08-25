package com.test.rainbowDefense.battle

import android.content.Context
import android.util.Log
import com.test.rainbowDefense.database.MonsterEntity
import com.test.rainbowDefense.database.UnitEntity
import java.util.*

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
        v.unit_array.forEach { it.move() }
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
    fun checkAttack() {

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
                vx = unit.moveSpeed / ping
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