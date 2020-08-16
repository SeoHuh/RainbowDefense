package com.test.rainbowDefense.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stage_table")
class StageEntity(
    @PrimaryKey(autoGenerate = true) val stage:Int,
    @ColumnInfo(name = "waveDifficulty") val waveDifficulty: Int,
    @ColumnInfo(name = "waveNum") val waveNum: Int,
    @ColumnInfo(name = "bossWave") val bossWave: Int,
    @ColumnInfo(name = "hpMag") val hpMag: Int,
    @ColumnInfo(name = "damageMag") val damageMag: Int,
    @ColumnInfo(name = "reward") val reward: Int
    )