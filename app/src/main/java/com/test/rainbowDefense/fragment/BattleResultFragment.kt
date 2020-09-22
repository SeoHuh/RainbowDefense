package com.test.rainbowDefense.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.StateEntity
import kotlinx.android.synthetic.main.battle_result.view.*


class BattleResultFragment(
    val stateEntity: StateEntity,
    val isWin : Boolean,
    val stage : Int,
    val stageReward : Int,
    val monsterReward : Int,
    val hpPercent : Int) : DialogFragment() {

    private var totalReward: Int = 0
    private var listener: NoticeDialogListener? = null
    fun setOnlistner(listener: NoticeDialogListener) {
        this.listener = listener
    }

    interface NoticeDialogListener {
        fun onExitClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.battle_result, null)
            if(isWin) {
                totalReward = stageReward * (100 + hpPercent) / 100 + monsterReward
                view.txt_result.text = "승리"
                view.stage_string2.text = stage.toString()
                view.reward_string2.text = stageReward.toString()
                view.monster_string2.text = monsterReward.toString()
                view.hp_string2.text = "$hpPercent%"
                view.total_reward_string2.text = totalReward.toString()
            }
            else {
                totalReward = monsterReward
                view.txt_result.text = "패배"
                view.stage_string2.text = stage.toString()
                view.reward_string2.text = "0"
                view.monster_string2.text = monsterReward.toString()
                view.hp_string2.text = "0%"
                view.total_reward_string2.text = totalReward.toString()
            }


            view.btn_exit.setOnClickListener{   // 나가기 버튼 클릭
                listener?.onExitClick(this)
                dialog?.cancel()
            }

            builder.setView(view)
            builder.create().apply{
                // 상태바 생성 방지
                window?.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                // 배경이 검은색으로 되는것 방지
                window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                // 배경을 투명색으로 지정
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                // Immersive_Sticky 모드 설정
                window?.decorView?.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

}