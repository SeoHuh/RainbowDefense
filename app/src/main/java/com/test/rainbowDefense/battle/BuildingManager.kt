package com.test.rainbowDefense.battle

import android.content.Context
import com.test.rainbowDefense.database.UnitEntity

class BuildingManager (
    val content : Context,
    val v : CanvasView,
    val effectManager: EffectManager,
    ping : Int,
    unitEntityList: List<UnitEntity>) {

    // 리스트에서 건물만 가져온다.
    val buildingList = unitEntityList.filter{ it.type =="building" }
    var selectedBuilding: UnitEntity? = null

    // 건물리스트의 드로어블을 설정
    init {
        buildingList.forEach {
            val resId: Int =
                v.resources.getIdentifier(it.resourceId, "drawable", "com.test.rainbowDefense")
            it.drawable = v.resources.getDrawable(resId, content.theme)
        }
    }

    fun showPosition() {

    }
}