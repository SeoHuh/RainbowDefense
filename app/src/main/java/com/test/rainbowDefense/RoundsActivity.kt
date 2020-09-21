package com.test.rainbowDefense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.adapter.OnItemClickListener
import com.test.rainbowDefense.adapter.RoundsAdapter
import com.test.rainbowDefense.adapter.ShopAdapter
import com.test.rainbowDefense.database.*
import kotlinx.android.synthetic.main.activity_rounds.*
import kotlinx.android.synthetic.main.activity_rounds.gold_text
import kotlinx.android.synthetic.main.activity_rounds.stage_text
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoundsActivity : AppCompatActivity() {

    private lateinit var stateViewModel: StateViewModel
    private lateinit var stage : List<StageEntity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rounds)
        setFullscreen()

        val intent_battle = Intent(this,BattleActivity::class.java)

        val intent = Intent(this, LobyActivity::class.java)

        btn_rounds_back.setOnClickListener {
            super.finish()
            overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_bottom)
        }

        val db = MyRoomDatabase.getDatabase(this)
        var stageSize = 0
        CoroutineScope(Dispatchers.IO).launch {
            stage = db.stageDao().get()
            stageSize = stage.size

            val viewManager = GridLayoutManager(this@RoundsActivity, 8)
            val viewAdapter = RoundsAdapter(this@RoundsActivity, stageSize)

            //뷰 어뎁터에 클릭리스너 적용
            viewAdapter.setOnItemClickListener(object :
                OnItemClickListener {
                override fun onItemClick(v: View, pos: Int) {
                    intent_battle.putExtra("Round",pos + 1)
                    startActivity(intent_battle)
                    this@RoundsActivity.finish()
                }
            })

            // 리사이클러뷰에 데이터 적용
            val recyclerView = findViewById<RecyclerView>(R.id.rounds_recyclerview)
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }

        stateViewModel = ViewModelProvider(this)[StateViewModel::class.java]
        stateViewModel.state.observe(this, Observer { state ->
            state?.let {
                stage_text.text = it[0].stage.toString()
                gold_text.text = it[0].gold.toString()
            }
            Log.d("디버깅", "State 변경확인")
        })
        gold_text.setOnClickListener {
            val state: StateEntity = stateViewModel.state.value!!.get(0)
            stateViewModel.update(state.apply { gold+=10 })
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setFullscreen()
    }

    private fun setFullscreen(){
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}