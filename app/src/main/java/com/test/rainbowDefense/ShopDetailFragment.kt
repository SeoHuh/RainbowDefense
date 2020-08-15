package com.test.rainbowDefense

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.test.rainbowDefense.adapter.OnItemClickListener
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
            view.image.setImageResource(resId)
            view.name.text = unit.name
            view.level.text = (unit.level.toString() + "Lv")
            view.button.setOnClickListener{
                listener?.onDialogPositiveClick(this)
                dialog?.cancel()
            }
            view.button2.setOnClickListener{
                listener?.onDialogNegativeClick(this)
                dialog?.cancel()
            }

            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}