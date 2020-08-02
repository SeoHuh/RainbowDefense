package com.test.rainbowDefense

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.adapter.ReinforceAdapter
import kotlinx.android.synthetic.main.activity_reinforce.*

class reinforce : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reinforce)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        val intent = Intent(this, loby::class.java)


        btn_reinforce_back.setOnClickListener{view ->
            startActivity(intent)
        }

        val red_color = Color.rgb(236,40,46)
        val orange_color = Color.rgb(254,102,18)
        val yellow_color = Color.rgb(254,233,68)
        val green_color = Color.rgb(58,205,36)
        val blue_color = Color.rgb(2,141,244)
        val indigo_color = Color.rgb(0,26,240)
        val purple_color = Color.rgb(151,86,244)

        val myDataset = arrayOf(
            MyUnit(red_color,R.drawable.red_circle, 1, "Fire Wizard"),
            MyUnit(red_color,R.drawable.red_circle, 1, "Fire Dancer"),
            MyUnit(orange_color,R.drawable.orange_circle, 1, "Thunder Wizard"),
            MyUnit(orange_color,R.drawable.orange_circle, 1, "Orange"),
            MyUnit(yellow_color,R.drawable.yellow_circle, 1, "Miner"),
            MyUnit(yellow_color,R.drawable.yellow_circle, 1, "Gold Bank"),
            MyUnit(green_color,R.drawable.green_circle, 1, "Druid"),
            MyUnit(green_color,R.drawable.green_circle, 1, "Bind"),
            MyUnit(blue_color,R.drawable.blue_circle, 1, "Ice Wizard"),
            MyUnit(blue_color,R.drawable.blue_circle, 1, "Ice Trap"),
            MyUnit(indigo_color,R.drawable.indigo_circle, 1, "Dark Wizard"),
            MyUnit(indigo_color,R.drawable.indigo_circle, 1, "Skeleton"),
            MyUnit(purple_color,R.drawable.purple_circle, 1, "Poison"),
            MyUnit(purple_color,R.drawable.purple_circle, 1, "Weakness")
        )

        viewManager = LinearLayoutManager(this)
        viewAdapter = ReinforceAdapter(myDataset)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }
}
