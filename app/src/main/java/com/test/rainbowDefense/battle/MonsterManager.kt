package com.test.rainbowDefense.battle

import android.content.Context
import android.util.Log
import com.test.rainbowDefense.database.MonsterEntity
import java.util.*

class MonsterManager (
    val content : Context,
    val v : CanvasView,
    val effectManager: EffectManager,
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
            monster = list[rand(0, list.size - 1)]
        }
        return monster
    }

    fun monsterMove() {
        v.monster_array.forEach { it.move() }
    }

    fun checkDead() {
        val arrayList = v.monster_array
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
        val arrayList = v.monster_array
        var n: Int = 0
        while(n < arrayList.size) {
            if(arrayList[n].x+arrayList[n].width<=0){
                arrayList.removeAt(n)
                n--
                v.status?.apply{
                    hp -= 2
                }
            }
            n++
        }
    }

    fun makeMonster(x: Int,y: Int,rank: Int) {
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
                vx = -monster.moveSpeed/ping
                attackDamage = monster.attackDamage
                attackSpeed = monster.attackSpeed
                attackRange = monster.attackRange
                hp = monster.hp
                reward = monster.reward
            }
        )
    }

    private val random = Random()
    private fun rand(from : Int, to : Int) : Int {
        return random.nextInt(to - from) + from
    }
}