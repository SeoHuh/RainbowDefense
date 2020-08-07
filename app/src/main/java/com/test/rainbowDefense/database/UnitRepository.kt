package com.test.rainbowDefense.database

import androidx.lifecycle.LiveData

class UnitRepository(private val unitDao: UnitDao) {

    val allUnits: LiveData<List<UnitEntity>> = unitDao.getAll()

    suspend fun insert(unit: UnitEntity) {
        unitDao.insert(unit)
    }
}