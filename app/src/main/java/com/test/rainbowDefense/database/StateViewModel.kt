package com.test.rainbowDefense.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StateViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StateRepository
    val state: LiveData<List<StateEntity>>

    init {
        val stateDao = MyRoomDatabase.getDatabase(application,viewModelScope).stateDao()
        repository = StateRepository(stateDao)
        state = repository.state
    }
    fun update(state:StateEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.update(state)
    }
}