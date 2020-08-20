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

    var blockX = x + 10
    val blockY = y + 10
    val blockWidth = 220
    val blockHeight = 220

    init {
        clearMenu()
        block_array.add(
            Block(
                blockX,
                blockY,
                blockWidth,
                blockHeight,
                blockDrawable,
                blockClickDrawable,
                "유닛"
            )
        )
        blockX += 230
        block_array.add(
            Block(
                blockX,
                blockY,
                blockWidth,
                blockHeight,
                blockDrawable,
                blockClickDrawable,
                "건물"
            )
        )
        blockX += 230
        block_array.add(
            Block(
                blockX,
                blockY,
                blockWidth,
                blockHeight,
                blockDrawable,
                blockClickDrawable,
                "스킬"
            )
        )
        blockX += 230
//        TODO("블록 메뉴 내부에서 블록을 전부 생성하도록 하고")
//        TODO("유닛 블록 클릭시 블록 전부 삭제후 유닛 블록만 보이도록 한다.")
//        TODO("뒤로가기 버튼 블록마다 드로어블을 넣어줄 수 있도록 여러가지 비트맵을 가져온다.")
    }
    fun clearMenu() {
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

}