package com.test.rainbowDefense.battle

import android.content.Context
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.UnitEntity
import kotlin.math.sqrt

class SkillManager(
    val content: Context,
    val v: CanvasView,
    val displayWidth: Int,
    val battleHeight: Int,
    val effectManager: EffectManager,
    ping: Int,
    unitEntityList: List<UnitEntity>
) {

    val skillDrawable = v.resources.getDrawable(R.drawable.skill_range, content.theme)

    // 리스트에서 스킬만 가져온다.
    val skillList = unitEntityList.filter { it.type == "skill" }
    var selectedSkill: UnitEntity? = null

    // 스킬리스트의 드로어블을 설정
    init {
        skillList.forEach {
            val resId: Int =
                v.resources.getIdentifier(it.resourceId, "drawable", "com.test.rainbowDefense")
            it.drawable = v.resources.getDrawable(resId, content.theme)
        }
    }

    // 스킬 관련
    fun startSkill(skill: UnitEntity) {
        selectedSkill = skill
        v.skillShape = Shape(
            displayWidth / 2 - skill.width / 2,
            battleHeight / 2 - skill.height / 2,
            skill.width,
            skill.height,
            skillDrawable
        )
    }

    fun endSkill(x: Int, y: Int) {
        selectedSkill?.let {
            v.monster_array.forEach { monster ->
                effectManager.makeEffect(x, y)
                effectManager.makeEffect(x - it.width / 2, y)
                effectManager.makeEffect(x, y - it.height / 2)
                effectManager.makeEffect(x + it.width / 2, y)
                effectManager.makeEffect(x, y + it.height / 2)
                val distance = findDistance(x, y, monster)
                if (distance <= it.attackRange) {
                    monster.hp -= it.attackDamage
                }
            }
            v.status?.apply {
                mana -= it.cost
            }
        }
        selectedSkill = null
        v.skillShape = null
    }

    private fun findDistance(x: Int, y: Int, monster: Monster): Double {
        val distanceX: Double = (x - (monster.x + monster.width / 2)).toDouble()
        val distanceY: Double = (y - (monster.y + monster.height / 2)).toDouble()
        val distance = sqrt(Math.pow(distanceX, 2.0) + Math.pow(distanceY, 2.0))
        return distance
    }

}