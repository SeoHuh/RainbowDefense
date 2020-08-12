package com.test.rainbowDefense.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StateDao {

    @Query("SELECT * FROM state_table")
    fun get(): LiveData<List<StateEntity>>

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(state: StateEntity)
}