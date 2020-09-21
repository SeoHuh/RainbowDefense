package com.test.rainbowDefense.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.UnitEntity

class RoundsAdapter internal constructor(val context:Context, val size:Int) :
    RecyclerView.Adapter<RoundsAdapter.MyViewHolder>() {

    private var itemClickListener : OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.itemClickListener = listener
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val roundsButton = view.findViewById<Button>(R.id.btn_rounds)
        init {
            roundsButton.setOnClickListener{
                val pos = adapterPosition
                Log.d("디버깅", "클릭됨$pos ")
                if(pos != RecyclerView.NO_POSITION){
                    itemClickListener?.onItemClick(view,pos)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoundsAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.round_btn, parent, false) as View

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.roundsButton.text = (position + 1).toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = size

}