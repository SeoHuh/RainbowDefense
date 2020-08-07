package com.test.rainbowDefense.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.MyUnit
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.UnitEntity

class ReinforceAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<ReinforceAdapter.MyViewHolder>() {

    private var units = emptyList<UnitEntity>()

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val id = view.findViewById<ImageView>(R.id.circle_image)
        val level = view.findViewById<TextView>(R.id.level_text)
        val name = view.findViewById<TextView>(R.id.name_text)
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

        holder.id.setImageResource(current.resourceId)
        holder.level.setTextColor(current.color)
        holder.name.setTextColor(current.color)

        holder.level.text = (current.level.toString()+"Lv")
        holder.name.text = (current.name)
    }

    override fun getItemCount() = units.size

    fun setUnits(units:List<UnitEntity>) {
        this.units = units
        notifyDataSetChanged()
    }
}

