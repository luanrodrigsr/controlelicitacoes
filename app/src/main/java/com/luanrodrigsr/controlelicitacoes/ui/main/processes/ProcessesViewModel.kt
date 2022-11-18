package com.luanrodrigsr.controlelicitacoes.ui.main.processes

import androidx.lifecycle.*
import com.luanrodrigsr.controlelicitacoes.data.database.Database
import com.luanrodrigsr.controlelicitacoes.data.model.Process
import io.realm.kotlin.Realm
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProcessesViewModel(
    private val database: Realm
) : ViewModel() {

    private var _allProcess: MutableLiveData<List<Process>> =
        MutableLiveData<List<Process>>().apply {
            value = Database.getFakeData()
        }
    val allProcess: LiveData<List<Process>> = _allProcess

    private var _processesListState = MutableLiveData<ProcessesListState>().apply {
        value = ProcessesListState.LOADING
    }

    val processesListState: LiveData<ProcessesListState> = _processesListState

    fun retrieveProcesses() {
        viewModelScope.launch {
            // *******************************
            // Aguardar 1 segundo para simular a latência na conexão de internet.
            // *******************************
            delay(1000L)

            allProcess.let {
                it.value?.let { list ->
                    if (list.isEmpty()) {
                        _processesListState.value = ProcessesListState.EMPTY
                    } else {
                        _processesListState.value = ProcessesListState.LOADED
                    }
                }
            }
        }
    }
}

enum class ProcessesListState {
    LOADING,
    LOADED,
    EMPTY
}

class ProcessesViewModelFactory(private val database: Realm) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProcessesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProcessesViewModel(database) as T
        } else {
            throw IllegalArgumentException("Classe de ViewModel desconhecida.")
        }
    }
}