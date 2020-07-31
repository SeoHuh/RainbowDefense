package com.test.rainbowDefense

import android.content.Intent
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

        val myDataset = arrayOf(
            "Red, 1lv, 화염마법사",
            "Orange, 1lv, 번개마법사",
            "Yellow, 1lv, 광부",
            "Green, 1lv, 드루이드",
            "Blue, 1lv, 얼음마법사",
            "indigo, 1lv, 흑마법사",
            "purple, 1lv, 독",
            "기타 건물 등등",
            "기타 업그레이드",
            "그 외 강화 목록")

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