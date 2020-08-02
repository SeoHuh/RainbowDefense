package com.test.rainbowDefense.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.MyUnit
import com.test.rainbowDefense.R

class ReinforceAdapter(private val myDataset: Array<MyUnit>) :
    RecyclerView.Adapter<ReinforceAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val id = view.findViewById<ImageView>(R.id.circle_image)
        val level = view.findViewById<TextView>(R.id.level_text)
        val name = view.findViewById<TextView>(R.id.name_text)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReinforceAdapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reinforce_view, parent, false) as View
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.id.setImageResource(myDataset[position].resourceId)
        holder.level.setTextColor(myDataset[position].color)
        holder.name.setTextColor(myDataset[position].color)

        holder.level.text = (myDataset[position].level.toString()+"Lv")
        holder.name.text = (myDataset[position].name)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}

