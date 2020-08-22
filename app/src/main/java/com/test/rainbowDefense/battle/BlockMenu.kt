package com.test.rainbowDefense.battle

import android.content.Context
import android.graphics.Canvas
import com.test.rainbowDefense.R

class BlockMenu(
    val content: Context,
    val block_array :ArrayList<Block>,
    val x : Int,
    val y : Int,
    val width: Int,
    val height: Int) {

    val menuDrawable = content.resources.getDrawable(R.drawable.menu_box, content.theme)
    val blockDrawable = content.resources.getDrawable(R.drawable.block_box, content.theme)
    val blockClickDrawable = content.resources.getDrawable(R.drawable.block_box, content.theme)

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
        block_array.removeAll(block_array)
        blockX = x + 10
        block_array.add(    // 블록 메뉴 UI 창
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
    private fun showMenu() {    // 초기 메뉴 (유닛, 건물, 스킬 메뉴)
        clearMenu()
        addBlock("유닛") {showUnit()}
        addBlock("건물") {showStructure()}
        addBlock("스킬") {showSkill()}
    }

    private fun showUnit() {
        clearMenu()
        addBlock("이전") {showMenu()}
        addBlock("유닛") {nothing()}
    }

    private fun showStructure() {
        clearMenu()
        addBlock("이전") {showMenu()}
        addBlock("건물") {nothing()}

    }

    private fun showSkill() {
        clearMenu()
        addBlock("이전") {showMenu()}
        addBlock("스킬") {nothing()}

    }

    private fun nextX() {
        blockX += blockWidth + blockPadding
    }

    private fun addBlock(string:String,function: () -> Unit) {
        block_array.add(
            Block(
                blockX,
                blockY,
                blockWidth,
                blockHeight,
                blockDrawable,
                blockClickDrawable,
                string
            ).apply{
                setOnlistner(object:
                    Block.BlockListener{
                    override fun onClick() {
                        function.invoke()
                    }
                })
            }
        )
        nextX()
    }
    private fun nothing(){}


}