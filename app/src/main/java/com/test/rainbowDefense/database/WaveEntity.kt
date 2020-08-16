package com.test.rainbowDefense.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wave_table")
class WaveEntity(
    @PrimaryKey(autoGenerate = true) val difficulty:Int,
    @ColumnInfo(name = "monsterNumber") val monsterNumber: Int,
    @ColumnInfo(name = "delayTime") val delayTime: Int,
    @ColumnInfo(name = "normalNumber") val normalNumber: Int,
    @ColumnInfo(name = "epicNumber") val epicNumber: Int,
    @ColumnInfo(name = "uniqueNumber") val uniqueNumber: Int,
    @ColumnInfo(name = "bossNumber") val bossNumber: Int

)