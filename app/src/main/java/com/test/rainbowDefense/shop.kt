package com.test.rainbowDefense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.adapter.ReinforceAdapter
import kotlinx.android.synthetic.main.activity_shop.*
import androidx.recyclerview.widget.GridLayoutManager
import com.test.rainbowDefense.adapter.ShopAdapter
import com.test.rainbowDefense.database.UnitEntity
import com.test.rainbowDefense.database.UnitViewModel

class shop : AppCompatActivity() {

    private lateinit var unitViewModel: UnitViewModel

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

        val viewManager = GridLayoutManager(this, 5)
        val viewAdapter = ShopAdapter(this)

        viewAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(v: View, pos: Int) {
                val unit: UnitEntity = unitViewModel.notHaveUnits.value!!.get(pos)
                unitViewModel.update(unit.apply{type=1})
            }
        })

        unitViewModel = ViewModelProvider(this).get(UnitViewModel::class.java)
        unitViewModel.notHaveUnits.observe(this, Observer { units ->
            units?.let { viewAdapter.setUnits(it) }
            Log.d("디버깅", "옵저버 실행")
        })

        val recyclerView = findViewById<RecyclerView>(R.id.shop_recyclerview)
        recyclerView.apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}