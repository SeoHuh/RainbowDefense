package com.test.rainbowDefense.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unit_table")
class UnitEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "color") val color: Int,
    @ColumnInfo(name = "resourceId") val resourceId: Int,
    @ColumnInfo(name = "level") var level: Int,
    @ColumnInfo(name = "name") val name: String)