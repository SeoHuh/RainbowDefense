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
    val haveUnits: LiveData<List<UnitEntity>>
    val notHaveUnits: LiveData<List<UnitEntity>>

    init {
        val unitDao = MyRoomDatabase.getDatabase(application).unitDao()
        repository = UnitRepository(unitDao)
        allUnits = repository.allUnits
        haveUnits = repository.haveUnits
        notHaveUnits = repository.notHaveUnits
    }
    fun insert(unit:UnitEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(unit)
    }
    fun update(unit:UnitEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.update(unit)
    }
}
