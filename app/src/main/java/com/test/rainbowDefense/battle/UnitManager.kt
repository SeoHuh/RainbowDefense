package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.UnitEntity
import java.util.*
import kotlin.math.atan2
import kotlin.math.sqrt

class UnitManager(
    val content: Context,
    val v: CanvasView,
    val effectManager: EffectManager,
    val damageManager: DamageManager,
    val arrowManager: ArrowManager,
    val ping: Int,
    unitEntityList: List<UnitEntity>
) {

    val unitList = unitEntityList.filter { it.type == "unit" }   // UnitEntity중에 유닛만 가져온다.

    init {
        unitList.forEach {
            val resId: Int =
                v.resources.getIdentifier(it.resourceId, "drawable", "com.test.rainbowDefense")
            it.drawable = v.resources.getDrawable(resId, content.theme)
            if(it.attackType == 2) { // 원거리 유닛이면 투사체 드로어블 가져오기
                val projectileId: Int =
                    v.resources.getIdentifier(it.projectileId, "drawable","com.test.rainbowDefense")
                it.projectileDrawable = v.resources.getDrawable(projectileId,content.theme)
            }
        }
    }

    fun unitMove() {
        v.unit_array.forEach { unit ->
            unitAI(unit)    // 이동전에 유닛의 행동 지정
            unit.move()
        }
    }

    fun checkDead() {   // 유닛의 사망을 판정
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

    fun attack(unit: MyUnit, monster: Monster) {     // 몬스터를 공격

        if (unit.time >= unit.attackDelay) {
            if (unit.attackType == 1) {
                effectManager.makeEffect(
                    monster.x + monster.width / 2,
                    monster.y + monster.height / 2
                )
                damageManager.makeDamage(
                    monster.x + monster.width / 2f,
                    monster.y + 10f,
                    unit.attackDamage,
                    unit.color
                )
                monster.hp -= unit.attackDamage
            }
            else if (unit.attackType == 2) {
                arrowManager.makeProjectile(
                    unit.x+unit.width,
                    unit.y + unit.height/2,
                    100,
                    100,
                    unit.projectileDrawable,
                    1000f/ping,
                    findAngle(unit,monster).toFloat(),
                    unit.attackDamage,
                    unit.color
                )
            }
            unit.time = 0
        }
    }

    fun makeUnit(x: Int, y: Int, unit: UnitEntity) {     // 유닛 인스턴스 생성
        Log.d("디버그", "makeUnit: ${unit.name}")
        v.unit_array.add(
            MyUnit(
                x,
                y,
                unit.width,
                unit.height,
                unit.drawable
            ).apply {
                setVelocity(unit.moveSpeed / ping.toFloat(), 0f)
                attackDamage = unit.attackDamage
                attackSpeed = unit.attackSpeed
                attackRange = unit.attackRange
                attackType = unit.attackType
                viewRange = unit.viewRange
                color = Color.parseColor(unit.color)
                hp = unit.hp
                projectileDrawable = unit.projectileDrawable
            }
        )
    }

    // 유닛의 AI, 가까운 몬스터 찾기, 몬스터와의 거리, 각도 측정
    private fun unitAI(unit: MyUnit) {
        if (v.monster_array.isNotEmpty()) {      // 몬스터가 있으면
            val closeMonster = findCloseMonster(unit)
            val closeDistance = findDistance(unit, closeMonster)
            val closeAngle = findAngle(unit, closeMonster)

            when {
                closeDistance <= unit.attackRange -> {    // 공격 범위 내부일 경우 공격
                    unit.stop()
                    attack(unit, closeMonster)
                }
                closeDistance <= unit.viewRange -> {      // 시야 범위 내부일 경우 이동
                    unit.setVelocity(unit.speed, closeAngle.toFloat())
                }
                else -> {       // 그 외 범위일 경우 정지
                    unit.stop()
                }
            }
        } else {       // 몬스터가 없으면 정지
            unit.stop()
        }
    }

    private fun findCloseMonster(unit: MyUnit): Monster {
        var closeMonster = v.monster_array[0]
        var closeDistance = findDistance(unit, closeMonster)

        v.monster_array.forEach { monster ->
            val distance = findDistance(unit, monster)
            if (distance <= closeDistance) {
                closeMonster = monster
                closeDistance = distance
            }
        }

        return closeMonster
    }

    private fun findDistance(unit: MyUnit, monster: Monster): Double {
        val distanceX: Double =
            ((unit.x + unit.width / 2) - (monster.x + monster.width / 2)).toDouble()
        val distanceY: Double =
            ((unit.y + unit.height / 2) - (monster.y + monster.height / 2)).toDouble()
        val distance = sqrt(Math.pow(distanceX, 2.0) + Math.pow(distanceY, 2.0))
        return distance
    }

    private fun findAngle(unit: MyUnit, monster: Monster): Double {
        val distanceX: Double =
            ((unit.x + unit.width / 2) - (monster.x + monster.width / 2)).toDouble()
        val distanceY: Double =
            ((unit.y + unit.height / 2) - (monster.y + monster.height / 2)).toDouble()
        val angle = atan2(-distanceY, -distanceX)
        return angle
    }

    private val random = Random()
    private fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }
}