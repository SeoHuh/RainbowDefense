package com.test.rainbowDefense

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.rainbowDefense.adapter.OnItemClickListener
import com.test.rainbowDefense.database.StateEntity
import com.test.rainbowDefense.database.StateViewModel
import com.test.rainbowDefense.database.UnitEntity
import kotlinx.android.synthetic.main.setting_battle.view.*
import kotlinx.android.synthetic.main.setting_loby.*
import kotlinx.android.synthetic.main.setting_loby.view.*
import kotlinx.android.synthetic.main.setting_loby.view.btn_exit
import kotlinx.android.synthetic.main.setting_loby.view.btn_mute
import kotlinx.android.synthetic.main.setting_loby.view.btn_vibrate
import kotlinx.android.synthetic.main.setting_loby.view.seekbar_effect
import kotlinx.android.synthetic.main.setting_loby.view.seekbar_music
import kotlinx.android.synthetic.main.shop_detail.view.*

class SettingBattleDetailFragment(val state:StateEntity) : DialogFragment() {

    private var listener: NoticeDialogListener? = null
    fun setOnlistner(listener: NoticeDialogListener) {
        this.listener = listener
    }

    interface NoticeDialogListener {
        fun onExitClick(dialog: DialogFragment)
        fun onMuteClick(view : View)
        fun onVibrateClick(view: View)
        fun onResumeClick(dialog: DialogFragment)
        fun musicProgressChanged(view: View, i: Int)
        fun effectProgressChanged(view: View, i: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.setting_battle, null)
            when(state.muteState){
                0->view.btn_mute.setBackgroundResource(R.drawable.notmute)
                1->view.btn_mute.setBackgroundResource(R.drawable.mute)
            }
            when(state.vibrateState){
                0->view.btn_vibrate.setBackgroundResource(R.drawable.novibration)
                1->view.btn_vibrate.setBackgroundResource(R.drawable.vibration)
            }
            view.seekbar_music.progress = state.musicVolume
            view.seekbar_effect.progress = state.effectVolume

            view.btn_x.setOnClickListener {  // X 버튼
                listener?.onResumeClick(this)
                dialog?.cancel()
            }
            view.btn_mute.setOnClickListener {  // 뮤트버튼 클릭
                listener?.onMuteClick(it)
            }
            view.btn_vibrate.setOnClickListener {   // 진동 버튼 클릭
                listener?.onVibrateClick(it)
            }
            view.btn_resume.setOnClickListener {    // 리줌 버튼 클릭
                listener?.onResumeClick(this)
                dialog?.cancel()
            }
            view.btn_exit.setOnClickListener{   // 나가기 버튼 클릭
                listener?.onExitClick(this)
                dialog?.cancel()
            }

            view.seekbar_music.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {    // 소리 조절 이벤트
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    listener?.musicProgressChanged(seekBar,i)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                }
            })
            view.seekbar_effect.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {   // 진동조절 이벤트
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    listener?.effectProgressChanged(seekBar,i)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                }
            })

            builder.setView(view)
            builder.create().apply{
                // 상태바 생성 방지
                window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
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




