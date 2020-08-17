package com.test.rainbowDefense.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StageDao {

    @Query("SELECT * FROM stage_table")
    fun get(): List<StageEntity>

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(stage: StageEntity)
}