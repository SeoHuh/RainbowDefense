package com.test.rainbowDefense

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.rainbowDefense.adapter.OnItemClickListener
import com.test.rainbowDefense.adapter.ReinforceAdapter
import com.test.rainbowDefense.database.StateEntity
import com.test.rainbowDefense.database.StateViewModel
import com.test.rainbowDefense.database.UnitEntity
import com.test.rainbowDefense.database.UnitViewModel
import kotlinx.android.synthetic.main.activity_loby.*
import kotlinx.android.synthetic.main.activity_reinforce.*
import kotlinx.android.synthetic.main.activity_reinforce.gold_text
import kotlinx.android.synthetic.main.activity_reinforce.stage_text

class ReinforceActivity : AppCompatActivity() {

    private lateinit var unitViewModel: UnitViewModel
    private lateinit var stateViewModel: StateViewModel
    private lateinit var selectUnit: UnitEntity

    private var isSelect = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reinforce)
        setFullScreen()

        btn_reinforce_back.setOnClickListener {
            super.finish()
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = ReinforceAdapter(this)

        viewAdapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(v: View, pos: Int) {
                val unit: UnitEntity = unitViewModel.haveUnits.value!!.get(pos)
                selectUnit = unit
                setUnit(selectUnit)
                isSelect = true
            }
        })

        var isRun = false
        unitViewModel = ViewModelProvider(this)[UnitViewModel::class.java]
        unitViewModel.haveUnits.observe(this, Observer { units ->
            units?.let { viewAdapter.setUnits(it) }
            if(!isRun) {
                setUnit(units[0])
                isRun = true
            }
        })

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
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
        buy_button.setOnClickListener{
            if(isSelect) {
                val state: StateEntity = stateViewModel.state.value!!.get(0)
                if (selectUnit.price <= state.gold) {
                    stateViewModel.update(state.apply { gold -= selectUnit.price })
                    unitViewModel.update(selectUnit.apply {
                        level++
                        hp += hpMag
                        attackDamage += attackDamageMag
                        price = priceBias + priceMag * level
                    })
                    setUnit(selectUnit)
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setFullScreen()
    }
    fun setUnit(unit:UnitEntity){
        val resId: Int =
            this.resources.getIdentifier(unit.resourceId, "drawable", "com.test.rainbowDefense")
        unit_image.setImageResource(resId)
        val color = Color.parseColor(unit.color)
        unit_level.setTextColor(color)
        unit_name.setTextColor(color)
        unit.price = unit.priceBias + unit.priceMag * (unit.level - 1)
        unit_price.text = unit.price.toString()
        unit_level.text = (unit.level.toString() + "Lv")
        unit_name.text = unit.name

        hp_string.text = "Hp: "
        unit_hp.text = unit.hp.toString()
        unit_hp_increase.text = ("+" + unit.hpMag.toString())
        damage_string.text = "Atk: "
        unit_damage.text = unit.attackDamage.toString()
        unit_damage_increase.text = ("+" + unit.attackDamageMag.toString())
        when(unit.name){
            "Castle Hp"-> {
                hp_string.text = "Hp: "
                unit_hp.text = unit.hp.toString()
                unit_hp_increase.text = ("+" + unit.hpMag.toString())
                damage_string.text = ""
                unit_damage.text = ""
                unit_damage_increase.text = ""
            }
            "Cloud Bank"-> {
                hp_string.text = "Gen: "
                unit_hp.text = unit.hp.toString()
                unit_hp_increase.text = ("+" + unit.hpMag.toString())
                damage_string.text = ""
                unit_damage.text = ""
                unit_damage_increase.text = ""
            }
            "Mana Max"-> {
                hp_string.text = "Max: "
                unit_hp.text = unit.hp.toString()
                unit_hp_increase.text = ("+" + unit.hpMag.toString())
                damage_string.text = ""
                unit_damage.text = ""
                unit_damage_increase.text = ""
            }
            "Mana Recovery"-> {
                hp_string.text = "Gen: "
                unit_hp.text = unit.hp.toString()
                unit_hp_increase.text = ("+" + unit.hpMag.toString())
                damage_string.text = ""
                unit_damage.text = ""
                unit_damage_increase.text = ""
            }
            "Arrow Damage"-> {
                hp_string.text = "Atk: "
                unit_hp.text = unit.hp.toString()
                unit_hp_increase.text = ("+" + unit.hpMag.toString())
                damage_string.text = ""
                unit_damage.text = ""
                unit_damage_increase.text = ""
            }
            "Arrow Agility"-> {
                hp_string.text = "Spd: "
                unit_hp.text = unit.hp.toString()
                unit_hp_increase.text = ("+" + unit.hpMag.toString())
                damage_string.text = ""
                unit_damage.text = ""
                unit_damage_increase.text = ""
            }
        }
    }

    fun setFullScreen() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}
