package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.UnitEntity
import java.util.*

// 블록 메뉴 ( 블록 매니저 )
// 게임 유저의 UI 를 구현하는 부분, 게임 하단의 버튼메뉴를 관리한다.

class BlockMenu(
    val content: Context,
    val v: CanvasView,
    val unitManager: UnitManager,
    val skillManager: SkillManager,
    val buildingManager: BuildingManager,
    val x : Int,
    val y : Int,
    val width: Int,
    val height: Int) {

    val menuDrawable = content.resources.getDrawable(R.drawable.menu_box, content.theme)
    val blockDrawable = content.resources.getDrawable(R.drawable.block_box, content.theme)
    val blockClickDrawable = content.resources.getDrawable(R.drawable.block_box, content.theme)
    val cloudDrawable = content.resources.getDrawable(R.drawable.cloud, content.theme)
    val manaDrawable = content.resources.getDrawable(R.drawable.blue_circle, content.theme)
    val skillRangeDrawable = content.resources.getDrawable(R.drawable.blue_circle,content.theme)

    val blockPadding = 10
    var blockX = x + blockPadding
    val blockY = y + blockPadding
    val blockWidth = 220
    val blockHeight = 220


    init {
        showMenu()

//        TODO("블록 메뉴 내부에서 블록을 전부 생성하도록 하고")
//        TODO("유닛 블록 클릭시 블록 전부 삭제후 유닛 블록만 보이도록 한다.")
//        TODO("뒤로가기 버튼 블록마다 드로어블을 넣어줄 수 있도록 여러가지 비트맵을 가져온다.")
    }
    private fun clearMenu() {
        v.block_array.removeAll(v.block_array)
        blockX = x + 10
        v.block_array.add(    // 블록 메뉴 UI 창+
            Block(
                x,
                y,
                width,
                height,
                menuDrawable,
                menuDrawable,
                ""
            ).apply{
                isClickable = false
            }
        )

    }
    private fun showMenu() : Boolean {    // 초기 메뉴 (유닛, 건물, 스킬 메뉴)
        clearMenu()
        addBlock("유닛",null,null,null) {showUnit()}
        addBlock("건물",null,null,null) {showBuilding()}
        addBlock("스킬",null,null,null) {showSkill()}
        return false
    }

    private fun showUnit() : Boolean {
        clearMenu()
        val unitList = unitManager.unitList
        addBlock("이전",null,null,null) {showMenu()}
        unitList.forEach{
            addBlock("",it.drawable,it.cost.toString(),cloudDrawable) {buyUnit(it)}
        }
        return false
    }
    private fun showBuilding() : Boolean {
        clearMenu()
        val buildingList = buildingManager.buildingList
        addBlock("이전",null,null,null) {showMenu()}
        buildingList.forEach{
            addBlock("",it.drawable,it.cost.toString(),cloudDrawable) {buyBuilding(it)}
        }
        return false
    }
    private fun showSkill() : Boolean {
        clearMenu()
        val skillList = skillManager.skillList
        addBlock("이전",null,null,null) {showMenu()}
        skillList.forEach{
            addBlock("",it.drawable,it.cost.toString(),manaDrawable) {useSkill(it)}
        }
        return false
    }

    // 블록 추가 (문자, 유닛 아이콘, 비용텍스트, 비용 배경) {실행할 함수}
    private fun addBlock(string:String, drawable: Drawable?, cost: String?, costDrawable: Drawable?, function: () -> Boolean) {
        v.block_array.add(
            Block(
                blockX,
                blockY,
                blockWidth,
                blockHeight,
                blockDrawable,
                blockClickDrawable,
                string
            ).apply{
                drawable?.let{
                    val bitmapDrawable: BitmapDrawable = it as BitmapDrawable
                    var bitmap: Bitmap = bitmapDrawable.bitmap
                    bitmap = Bitmap.createScaledBitmap(bitmap, blockWidth - blockPadding * 6, blockHeight - blockPadding * 6, false)
                    innerBitmap = bitmap
                }
                cost?.let{
                    costString = it
                }
                costDrawable?.let{
                    val bitmapDrawable: BitmapDrawable = it as BitmapDrawable
                    var bitmap: Bitmap = bitmapDrawable.bitmap
                    bitmap = Bitmap.createScaledBitmap(bitmap, blockPadding * 7, blockPadding * 5, false)
                    costBitmap = bitmap
                }
                setOnlistner(object:
                    Block.BlockListener{
                    override fun onClick() :Boolean{
                        return function.invoke()
                    }
                })
            }
        )
        nextX()
    }

    // 블록 클릭시 실행할 함수들
    private fun nothing(): Boolean{ return false }
    private fun buyUnit(unit: UnitEntity): Boolean{
        if(unit.cost<= v.status!!.cloud){   // 비용 충분할시 유닛 생성
            v.status?.apply {
                cloud -= unit.cost
            }
            unitManager.makeUnit(rand(0,100),rand(100,800),unit)
            return true
        }
        return false
    }
    private fun useSkill(skill: UnitEntity): Boolean{
        if(skill.cost<= v.status!!.cloud) {     // 비용 충분할 시 스킬 실행
            skillManager.selectSkill(skill)
            v.skillShape = Shape(0,0,skill.width,skill.height,skillRangeDrawable)
            return true
        }
        return false
    }
    private fun buyBuilding(building: UnitEntity): Boolean{
        if(building.cost<= v.status!!.cloud) {      // 비용 충분할 시 건물 생성
            return true
        }
        return false
    }


    private fun nextX() {
        blockX += blockWidth + blockPadding
    }

    private val random = Random()
    private fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }
}