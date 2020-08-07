package com.test.rainbowDefense.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UnitDao {
    @Query("SELECT * FROM unit_table")
    fun getAll(): LiveData<List<UnitEntity>>

    @Query("SELECT * from unit_table ORDER BY id ASC")
    fun getAlphabetizedWords(): LiveData<List<UnitEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(unit: UnitEntity)

    @Query("DELETE FROM unit_table")
    suspend fun deleteAll()
}