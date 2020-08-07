package com.test.rainbowDefense.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UnitViewModel(application: Application) :AndroidViewModel(application) {

    private val repository: UnitRepository
    val allUnits: LiveData<List<UnitEntity>>

    init {
        val unitDao = UnitRoomDatabase.getDatabase(application,viewModelScope).unitDao()
        repository = UnitRepository(unitDao)
        allUnits = repository.allUnits
    }
    fun insert(unit:UnitEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(unit)
    }
}
