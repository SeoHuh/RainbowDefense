package com.test.rainbowDefense

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.adapter.OnItemClickListener
import com.test.rainbowDefense.adapter.ShopAdapter
import com.test.rainbowDefense.database.StateEntity
import com.test.rainbowDefense.database.StateViewModel
import com.test.rainbowDefense.database.UnitEntity
import com.test.rainbowDefense.database.UnitViewModel
import com.test.rainbowDefense.fragment.ShopDetailFragment
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.activity_shop.gold_text
import kotlinx.android.synthetic.main.activity_shop.stage_text

class ShopActivity : AppCompatActivity() {

    private lateinit var unitViewModel: UnitViewModel
    private lateinit var stateViewModel: StateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        setFullScreen()

        val intent = Intent(this, LobyActivity::class.java)

        btn_shop_back.setOnClickListener {
            super.finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

        val viewManager = GridLayoutManager(this, 5)
        val viewAdapter = ShopAdapter(this)

        //뷰 어뎁터에 클릭리스너 적용
        viewAdapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(v: View, pos: Int) {
                val unit: UnitEntity = unitViewModel.notHaveUnits.value!!.get(pos)
                showDetail(unit)
            }
        })

        // 유닛 뷰모델 호출 후 관찰
        unitViewModel = ViewModelProvider(this).get(UnitViewModel::class.java)
        unitViewModel.notHaveUnits.observe(this, Observer { units ->
            units?.let { viewAdapter.setUnits(it) }
            Log.d("디버깅", "옵저버 실행")
        })

        // 리사이클러뷰에 데이터 적용
        val recyclerView = findViewById<RecyclerView>(R.id.shop_recyclerview)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
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
        setFullScreen()
    }

    fun showDetail(unit: UnitEntity) {
        val newFragment =
            ShopDetailFragment(unit)
        newFragment.show(supportFragmentManager, "mine")
        newFragment.setOnlistner(object :
            ShopDetailFragment.NoticeDialogListener {
            override fun onDialogPositiveClick(dialog: DialogFragment) {
                val state: StateEntity = stateViewModel.state.value!!.get(0)
                if(unit.priceShop<=state.gold){
                    stateViewModel.update(state.apply { gold -= unit.priceShop })
                    unitViewModel.update(unit.apply {
                        sellType = 1
                        price = priceBias + priceMag * level
                    })
                }
            }
            override fun onDialogNegativeClick(dialog: DialogFragment) {

            }
        })
    }

    private fun setFullScreen() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

}