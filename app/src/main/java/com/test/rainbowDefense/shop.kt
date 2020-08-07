package com.test.rainbowDefense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.adapter.ReinforceAdapter
import kotlinx.android.synthetic.main.activity_shop.*
import androidx.recyclerview.widget.GridLayoutManager
import com.test.rainbowDefense.adapter.ShopAdapter

class shop : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        val intent = Intent(this, loby::class.java)


        btn_shop_back.setOnClickListener {
            startActivity(intent)
        }

        val myDataSet = arrayOf(
            MyUnit(0,R.drawable.boss1, 0, "사고싶지"),
            MyUnit(0,R.drawable.boss2, 0, "안살거야?"),
            MyUnit(0,R.drawable.boss3, 0, "사고싶지"),
            MyUnit(0,R.drawable.boss4, 0, "사고싶지"),
            MyUnit(0,R.drawable.boss5, 0, "사고싶지"),
            MyUnit(0,R.drawable.boss6, 0, "사게될거야")
        )
        viewManager = GridLayoutManager(this, 5)
        viewAdapter = ShopAdapter(myDataSet)

        recyclerView = findViewById<RecyclerView>(R.id.shop_recyclerview).apply {
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