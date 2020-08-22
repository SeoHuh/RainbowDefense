package com.test.rainbowDefense

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.rainbowDefense.adapter.OnItemClickListener
import com.test.rainbowDefense.database.StateEntity
import com.test.rainbowDefense.database.StateViewModel
import com.test.rainbowDefense.database.UnitEntity
import kotlinx.android.synthetic.main.setting_loby.*
import kotlinx.android.synthetic.main.setting_loby.view.*
import kotlinx.android.synthetic.main.shop_detail.view.*

class SettingLobyDetailFragment() : DialogFragment() {

    private lateinit var stateViewModel: StateViewModel
    private lateinit var state : StateEntity

    private var listener: NoticeDialogListener? = null
    fun setOnlistner(listener: NoticeDialogListener) {
        this.listener = listener
    }

    interface NoticeDialogListener {
        fun onExitClick(dialog: DialogFragment)
        fun onMuteClick(dialog: DialogFragment)
        fun onVibrateClick(dialog: DialogFragment)
        fun onCouponClick(dialog: DialogFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateViewModel = ViewModelProvider(this)[StateViewModel::class.java]
        stateViewModel.state.observe(this, Observer { state ->
            state?.let {
                this.state = stateViewModel.state.value!!.get(0)
            }
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.setting_loby, null)
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

            view.btn_exit.setOnClickListener {
                listener?.onExitClick(this)
                dialog?.cancel()
            }
            view.btn_mute.setOnClickListener {
                listener?.onMuteClick(this)
                stateViewModel.update(state.apply {
                    if (this.muteState == 0) {
                        this.muteState = 1
                        it.setBackgroundResource(R.drawable.mute)
                    } else {
                        this.muteState = 0
                        it.setBackgroundResource(R.drawable.notmute)
                    }
                })
            }
            view.btn_vibrate.setOnClickListener {
                listener?.onVibrateClick(this)
                stateViewModel.update(state.apply {
                    if (this.vibrateState == 0) {
                        this.vibrateState = 1
                        it.setBackgroundResource(R.drawable.vibration)
                    } else {
                        this.vibrateState = 0
                        it.setBackgroundResource(R.drawable.novibration)
                    }
                })
            }

            view.btn_coupon.setOnClickListener {
                listener?.onCouponClick(this)
            }

            view.seekbar_music.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    val state: StateEntity = stateViewModel.state.value!!.get(0)
                    stateViewModel.update(state.apply { musicVolume = i })
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                }
            })
            view.seekbar_effect.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    val state: StateEntity = stateViewModel.state.value!!.get(0)
                    stateViewModel.update(state.apply { effectVolume = i })
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                }
            })


            builder.setView(view)
            builder.create().apply {
                window?.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                )
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




