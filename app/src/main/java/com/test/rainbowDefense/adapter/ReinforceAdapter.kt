package com.test.rainbowDefense.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.UnitEntity

class ReinforceAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<ReinforceAdapter.MyViewHolder>() {

    private var itemClickListener : OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.itemClickListener = listener
    }

    private var units = emptyList<UnitEntity>()

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val id = view.findViewById<ImageView>(R.id.circle_image)
        val level = view.findViewById<TextView>(R.id.level_text)
        val name = view.findViewById<TextView>(R.id.name_text)
        init {
            view.setOnClickListener{
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
    ): ReinforceAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reinforce_view, parent, false) as View

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val current = units[position]

        val resId: Int =
            holder.view.resources.getIdentifier(current.resourceId, "drawable", "com.test.rainbowDefense")
        holder.id.setImageResource(resId)
        holder.level.setTextColor(Color.parseColor(current.color))
        holder.name.setTextColor(Color.parseColor(current.color))

        holder.level.text = (current.level.toString()+"Lv")
        holder.name.text = (current.name)
    }

    override fun getItemCount() = units.size

    fun setUnits(units:List<UnitEntity>) {
        this.units = units
        notifyDataSetChanged()
    }
}

