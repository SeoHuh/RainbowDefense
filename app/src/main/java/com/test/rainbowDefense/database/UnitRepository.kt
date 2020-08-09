package com.test.rainbowDefense.database

import androidx.lifecycle.LiveData

class UnitRepository(private val unitDao: UnitDao) {

    val allUnits: LiveData<List<UnitEntity>> = unitDao.getAll()
    val haveUnits: LiveData<List<UnitEntity>> = unitDao.getHave()
    val notHaveUnits: LiveData<List<UnitEntity>> = unitDao.getNotHave()


    suspend fun insert(unit: UnitEntity) {
        unitDao.insert(unit)
    }

    suspend fun update(unit: UnitEntity) {
        unitDao.update(unit)
    }
}