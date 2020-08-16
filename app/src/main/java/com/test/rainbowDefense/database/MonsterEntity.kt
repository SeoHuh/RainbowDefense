package com.test.rainbowDefense.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monster_table")
class MonsterEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "resourceId") val resourceId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "reward") val reward: Int,
    @ColumnInfo(name = "hp") val hp: Int,
    @ColumnInfo(name = "attackDamage") val attackDamage: Int,
    @ColumnInfo(name = "attackSpeed") val attackSpeed: Int,
    @ColumnInfo(name = "moveSpeed") val moveSpeed: Int,
    @ColumnInfo(name = "rank") val rank: Int

)