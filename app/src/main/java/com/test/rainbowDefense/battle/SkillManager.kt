package com.test.rainbowDefense.battle

import android.content.Context
import com.test.rainbowDefense.database.UnitEntity
import kotlin.math.sqrt

class SkillManager(
    val content : Context,
    val v : CanvasView,
    val effectManager: EffectManager,
    ping : Int,
    unitEntityList: List<UnitEntity>) {

    // 리스트에서 스킬만 가져온다.
    val skillList = unitEntityList.filter{ it.type =="skill" }
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
    fun selectSkill(unit: UnitEntity) {
        selectedSkill = unit
    }
    fun useSkill(x: Int, y:Int) {
        selectedSkill?.let{
            v.monster_array.forEach { monster ->
                val distance = findDistance(x,y,monster)
                if(distance <= it.attackRange){
                    monster.hp -= it.attackDamage
                }
            }
        }
    }
    private fun findDistance(x: Int,y: Int,monster: Monster): Double{
        val distanceX:Double = (x - (monster.x + monster.width / 2)).toDouble()
        val distanceY:Double = (y - (monster.y + monster.height / 2)).toDouble()
        val distance = sqrt(Math.pow(distanceX,2.0) + Math.pow(distanceY,2.0))
        return distance
    }

}