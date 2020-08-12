package com.test.rainbowDefense.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.R
import com.test.rainbowDefense.database.UnitEntity

class ShopAdapter internal constructor(val context: Context) :
    RecyclerView.Adapter<ShopAdapter.MyViewHolder>() {

    private var itemClickListener : OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.itemClickListener = listener
    }

    private var units = emptyList<UnitEntity>()

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val imgView = view.findViewById<ImageView>(R.id.shop_image)
        val textView = view.findViewById<TextView>(R.id.shop_text)
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
    ): ShopAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_view, parent, false) as View

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = units[position]

        val resId: Int =
            holder.view.resources.getIdentifier(current.resourceId, "drawable", "com.test.rainbowDefense")
        holder.imgView.setImageResource(resId)
        holder.textView.text = current.name
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = units.size

    fun setUnits(units:List<UnitEntity>) {
        this.units = units
        notifyDataSetChanged()
    }

}