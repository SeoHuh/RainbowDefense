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


@Database(entities = [UnitEntity::class], version = 3, exportSchema = false)
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
                    .fallbackToDestructiveMigration()
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

            var unit = UnitEntity(101, red_color, R.drawable.red_circle, 1, "Fire Wizard",1)
            unitDao.insert(unit)
            unit = UnitEntity(102, red_color, R.drawable.red_circle, 1, "Fire Dancer",1)
            unitDao.insert(unit)
            unit = UnitEntity(201, orange_color, R.drawable.orange_circle, 1, "Thunder Wizard",1)
            unitDao.insert(unit)
            unit = UnitEntity(202, orange_color, R.drawable.orange_circle, 1, "Orange",1)
            unitDao.insert(unit)
            unit = UnitEntity(301, yellow_color, R.drawable.yellow_circle, 1, "Miner",1)
            unitDao.insert(unit)
            unit = UnitEntity(302, yellow_color, R.drawable.yellow_circle, 1, "Gold Bank",1)
            unitDao.insert(unit)
            unit = UnitEntity(401, green_color, R.drawable.green_circle, 1, "Druid",1)
            unitDao.insert(unit)
            unit = UnitEntity(402, green_color, R.drawable.green_circle, 1, "Bind",1)
            unitDao.insert(unit)
            unit = UnitEntity(501, blue_color, R.drawable.blue_circle, 1, "Ice Wizard",1)
            unitDao.insert(unit)
            unit = UnitEntity(502, blue_color, R.drawable.blue_circle, 1, "Ice Trap",1)
            unitDao.insert(unit)
            unit = UnitEntity(601, indigo_color, R.drawable.indigo_circle, 1, "Dark Wizard",1)
            unitDao.insert(unit)
            unit = UnitEntity(602, indigo_color, R.drawable.indigo_circle, 1, "Skeleton",1)
            unitDao.insert(unit)
            unit = UnitEntity(701, purple_color, R.drawable.purple_circle, 1, "Poison",1)
            unitDao.insert(unit)
            unit = UnitEntity(702, purple_color, R.drawable.purple_circle, 1, "Weakness",1)
            unitDao.insert(unit)
            unit = UnitEntity(103, red_color, R.drawable.red_circle, 1, "Noname",2)
            unitDao.insert(unit)
            unit = UnitEntity(203, orange_color, R.drawable.orange_circle, 1, "Noname",2)
            unitDao.insert(unit)
            unit = UnitEntity(303, yellow_color, R.drawable.yellow_circle, 1, "Noname",2)
            unitDao.insert(unit)
            unit = UnitEntity(403, green_color, R.drawable.green_circle, 1, "Noname",2)
            unitDao.insert(unit)
            unit = UnitEntity(503, blue_color, R.drawable.blue_circle, 1, "Noname",2)
            unitDao.insert(unit)
            unit = UnitEntity(603, indigo_color, R.drawable.indigo_circle, 1, "Noname",2)
            unitDao.insert(unit)
            unit = UnitEntity(703, purple_color, R.drawable.purple_circle, 1, "Noname",2)
            unitDao.insert(unit)

        }
    }
}