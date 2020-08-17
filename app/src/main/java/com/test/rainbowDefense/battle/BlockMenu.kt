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
    val blockDrawable = content.resources.getDrawable(R.drawable.text_box, content.theme)
    val blockClickDrawable = content.resources.getDrawable(R.drawable.text_box, content.theme)

    var blockX = x + 10
    val blockY = y + 10
    val blockWidth = 230
    val blockHeight = 220

    init {
        block_array.add(    // 블록 메뉴 UI 창
            Block(
                x,
                y,
                width,
                height,
                blockDrawable,
                blockClickDrawable
            ).apply{
                isClickable = false
            }
        )
        block_array.add(
            Block(
                blockX,
                blockY,
                blockWidth,
                blockHeight,
                blockDrawable,
                blockClickDrawable
            )
        )
        blockX += 240
        block_array.add(
            Block(
                blockX,
                blockY,
                blockWidth,
                blockHeight,
                blockDrawable,
                blockClickDrawable
            )
        )
        blockX += 240
        block_array.add(
            Block(
                blockX,
                blockY,
                blockWidth,
                blockHeight,
                blockDrawable,
                blockClickDrawable
            )
        )
        blockX += 240
//        TODO("블록 메뉴 내부에서 블록을 전부 생성하도록 하고")
//        TODO("유닛 블록 클릭시 블록 전부 삭제후 유닛 블록만 보이도록 한다.")
//        TODO("뒤로가기 버튼 블록마다 드로어블을 넣어줄 수 있도록 여러가지 비트맵을 가져온다.")
    }

}