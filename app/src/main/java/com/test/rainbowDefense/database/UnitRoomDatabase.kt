package com.test.rainbowDefense.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope


@Database(entities = [UnitEntity::class], version = 1, exportSchema = false)
abstract class UnitRoomDatabase : RoomDatabase() {

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
                    //.addCallback(UnitDatabaseCallback(context,scope))
                    .createFromAsset("database/rainbow.db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}