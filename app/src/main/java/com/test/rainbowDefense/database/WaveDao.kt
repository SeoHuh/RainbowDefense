package com.test.rainbowDefense.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WaveDao {

    @Query("SELECT * FROM wave_table")
    fun get(): List<WaveEntity>

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(wave: WaveEntity)
}