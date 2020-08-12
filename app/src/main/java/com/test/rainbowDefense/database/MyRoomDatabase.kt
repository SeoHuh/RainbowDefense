package com.test.rainbowDefense.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope


@Database(entities = [UnitEntity::class,StateEntity::class], version = 2, exportSchema = false)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun unitDao(): UnitDao
    abstract fun stateDao(): StateDao

    companion object {
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRoomDatabase::class.java,
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