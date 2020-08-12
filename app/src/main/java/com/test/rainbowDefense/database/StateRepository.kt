package com.test.rainbowDefense.database

import androidx.lifecycle.LiveData

class StateRepository(private val stateDao: StateDao) {

    val state= stateDao.get()

    suspend fun update(state: StateEntity) {
        stateDao.update(state)
    }
}