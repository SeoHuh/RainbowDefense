package com.test.rainbowDefense.database

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "unit_table")
class UnitEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "resourceId") val resourceId: String,
    @ColumnInfo(name = "level") var level: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "sellType") var sellType: Int,
    @ColumnInfo(name = "priceBias") var priceBias: Int,
    @ColumnInfo(name = "priceMag") var priceMag: Int,
    @ColumnInfo(name = "priceShop") var priceShop: Int,
    @ColumnInfo(name = "width") var width: Int,
    @ColumnInfo(name = "height") var height: Int,
    @ColumnInfo(name = "cost") var cost: Int,
    @ColumnInfo(name = "hp") var hp: Int,
    @ColumnInfo(name = "hpMag") var hpMag: Int,
    @ColumnInfo(name = "attackDamage") var attackDamage: Int,
    @ColumnInfo(name = "attackDamageMag") var attackDamageMag: Int,
    @ColumnInfo(name = "attackSpeed") var attackSpeed: Int,
    @ColumnInfo(name = "attackRange") var attackRange: Int,
    @ColumnInfo(name = "viewRange") var viewRange: Int,
    @ColumnInfo(name = "attackType") var attackType: Int,
    @ColumnInfo(name = "moveSpeed") var moveSpeed: Int,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "projectileId") var projectileId: String
){
    @Ignore var price = priceBias + priceMag * level
    @Ignore var drawable: Drawable? = null
    @Ignore var projectileDrawable: Drawable? = null
}