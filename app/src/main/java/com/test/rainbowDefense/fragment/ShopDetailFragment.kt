package com.test.rainbowDefense.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.UnitEntity
import kotlinx.android.synthetic.main.shop_detail.view.*

class ShopDetailFragment(val unit: UnitEntity) : DialogFragment() {

    private var listener : NoticeDialogListener? = null
    fun setOnlistner(listener: NoticeDialogListener){
        this.listener = listener
    }

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.shop_detail, null)
            val resId: Int =
                view.resources.getIdentifier(unit.resourceId, "drawable", "com.test.rainbowDefense")
            val color = Color.parseColor(unit.color)

            view.unit_name.setTextColor(color)
            view.unit_name.text = unit.name
            view.unit_level.setTextColor(color)
            view.unit_level.text = (unit.level.toString() + "Lv")
            view.unit_hp.text = unit.hp.toString()
            view.unit_damage.text = unit.attackDamage.toString()
            view.unit_price.text = unit.priceShop.toString()
            view.button.setOnClickListener{
                listener?.onDialogPositiveClick(this)
                dialog?.cancel()
            }
            view.button2.setOnClickListener{
                listener?.onDialogNegativeClick(this)
                dialog?.cancel()
            }

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



