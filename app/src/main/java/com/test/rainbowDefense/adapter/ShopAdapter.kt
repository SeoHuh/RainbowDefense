package com.test.rainbowDefense.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.R

class ShopAdapter(private val myDataset: Array<Int>) :
    RecyclerView.Adapter<ShopAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val imgView: ImageView) : RecyclerView.ViewHolder(imgView)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopAdapter.MyViewHolder {
        // create a new view
        val imgView = LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_view, parent, false) as ImageView

        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(imgView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.imgView.setImageResource(myDataset[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}