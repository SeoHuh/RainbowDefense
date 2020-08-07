package com.test.rainbowDefense.database

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.test.rainbowDefense.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [UnitEntity::class], version = 2, exportSchema = false)
public abstract class UnitRoomDatabase : RoomDatabase() {

    abstract fun unitDao(): UnitDao

    companion object {
        @Volatile
        private var INSTANCE: UnitRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): UnitRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UnitRoomDatabase::class.java,
                    "unit_database"
                )
                    .addCallback(UnitDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    private class UnitDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.unitDao())
                }
            }
        }

        suspend fun populateDatabase(unitDao: UnitDao) {
            unitDao.deleteAll()

            Log.d("디버깅", "데이터베이스 생성 ")
            val red_color = Color.rgb(236, 40, 46)
            val orange_color = Color.rgb(254, 102, 18)
            val yellow_color = Color.rgb(254, 233, 68)
            val green_color = Color.rgb(58, 205, 36)
            val blue_color = Color.rgb(2, 141, 244)
            val indigo_color = Color.rgb(0, 26, 240)
            val purple_color = Color.rgb(151, 86, 244)

            var unit = UnitEntity(1, red_color, R.drawable.red_circle, 1, "Fire Wizard")
            unitDao.insert(unit)
            unit = UnitEntity(2, red_color, R.drawable.red_circle, 1, "Fire Dancer")
            unitDao.insert(unit)
            unit = UnitEntity(3, orange_color, R.drawable.orange_circle, 1, "Thunder Wizard")
            unitDao.insert(unit)
            unit = UnitEntity(4, orange_color, R.drawable.orange_circle, 1, "Orange")
            unitDao.insert(unit)
            unit = UnitEntity(5, yellow_color, R.drawable.yellow_circle, 1, "Miner")
            unitDao.insert(unit)
            unit = UnitEntity(6, yellow_color, R.drawable.yellow_circle, 1, "Gold Bank")
            unitDao.insert(unit)
            unit = UnitEntity(7, green_color, R.drawable.green_circle, 1, "Druid")
            unitDao.insert(unit)
            unit = UnitEntity(8, green_color, R.drawable.green_circle, 1, "Bind")
            unitDao.insert(unit)
            unit = UnitEntity(9, blue_color, R.drawable.blue_circle, 1, "Ice Wizard")
            unitDao.insert(unit)
            unit = UnitEntity(10, blue_color, R.drawable.blue_circle, 1, "Ice Trap")
            unitDao.insert(unit)
            unit = UnitEntity(11, indigo_color, R.drawable.indigo_circle, 1, "Dark Wizard")
            unitDao.insert(unit)
            unit = UnitEntity(12, indigo_color, R.drawable.indigo_circle, 1, "Skeleton")
            unitDao.insert(unit)
            unit = UnitEntity(13, purple_color, R.drawable.purple_circle, 1, "Poison")
            unitDao.insert(unit)
            unit = UnitEntity(14, purple_color, R.drawable.purple_circle, 1, "Weakness")
            unitDao.insert(unit)
        }
    }
}