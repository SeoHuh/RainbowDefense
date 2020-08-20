package com.test.rainbowDefense.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MonsterDao {

    @Query("SELECT * FROM monster_table")
    fun get(): List<MonsterEntity>

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(monster: MonsterEntity)
}