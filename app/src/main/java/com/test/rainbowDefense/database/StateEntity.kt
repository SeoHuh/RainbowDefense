package com.test.rainbowDefense.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "state_table")
class StateEntity(
    @PrimaryKey var stage: Int,
    @ColumnInfo(name = "gold") var gold: Int,
    @ColumnInfo(name = "musicVolume") var musicVolume: Int,
    @ColumnInfo(name = "effectVolume") var effectVolume: Int,
    @ColumnInfo(name = "muteState") var muteState: Int,
    @ColumnInfo(name = "vibrateState") var vibrateState: Int
)