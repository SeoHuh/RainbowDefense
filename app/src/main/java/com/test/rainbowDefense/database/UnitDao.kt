package com.test.rainbowDefense.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UnitDao {

    @Query("SELECT * FROM unit_table")
    fun getAll(): LiveData<List<UnitEntity>>

    @Query("SELECT * FROM unit_table WHERE sellType=1 ORDER BY id ASC")
    fun getHaveList(): List<UnitEntity>

    @Query("SELECT * FROM unit_table WHERE sellType=1 ORDER BY id ASC")
    fun getHave(): LiveData<List<UnitEntity>>

    @Query("SELECT * FROM unit_table WHERE sellType=2")
    fun getNotHave(): LiveData<List<UnitEntity>>

    @Query("SELECT * FROM unit_table ORDER BY id ASC")
    fun getAlphabetizedWords(): LiveData<List<UnitEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(unit: UnitEntity)

    @Query("DELETE FROM unit_table")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(unit: UnitEntity)
}