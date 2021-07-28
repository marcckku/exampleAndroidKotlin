package com.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.model.User
import com.mvvm.repository.DbManager
import com.mvvm.utils.ApplicationGlobal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ListUserDetailsSettingsViewModel
@Inject constructor(
    private val dbManager: DbManager,
) : ViewModel() {

    private val TAG: String = ListUserDetailsSettingsViewModel::class.java.simpleName
    private val dispatchers: CoroutineContext = Dispatchers.IO
    private var coroutineScope: CoroutineScope = CoroutineScope(dispatchers + SupervisorJob())
    var liveDataUsers: MutableLiveData<List<User>> = MutableLiveData<List<User>>()
    var states: MutableLiveData<ListUsersScreenStates> = MutableLiveData<ListUsersScreenStates>()

    init {
        Log.e(TAG, "INJECTION VALUE dbInMemory != null  ${dbManager != null}")
        println("dbManager.getDatabaseSingleton() ISTANCE 1 ${dbManager.getDatabaseSingleton()}")
        println("dbManager.getDatabaseSingleton() ISTANCE 2 ${dbManager.getDatabaseSingleton()}")
        //dbManager.deleteAllRowsAllTables() //ESEGUI PULIZIA DB UNA VOLTA, E POI COMMENTALO DI NUOVO
    }

    /**
     * ###### SOLUZIONE AGGIORNAMENTI IN TEMPO REALE, NELLA LISTA!! ######
     * Qui abbiamo usato getAllUsersFlow() senza suspend con il tipo di dato
     * Flow<Array<User>> perchè con Flow<List<User>> lancia errore a tempo di
     * compilazione.!! Il tutto dentro coroutineScope.launch { ... } questo
     * per non eseguire sul Thread principale, rispettando il flusso di dati
     * nel ViewModel. Continua lettura nel file readme.md
     * */
    fun updateListLiveState(event: ListScreenEvents) {
        when (event) {
            ListScreenEvents.GetCurrentListUsersEvent -> {
                Log.e(TAG, "GetCurrentListUsersEvent")
                coroutineScope.launch {
                    dbManager.getAllUsersFlow().collect {users ->
                        if(users.isNullOrEmpty()){
                            Log.e(TAG, "users fakes or api rest!!")
                            ApplicationGlobal.usersFake.forEach {user ->
                                dbManager.insertUser(user)
                            }
                            ListUsersScreenStates.ListContentState.users =  ApplicationGlobal.usersFake
                            liveDataUsers.postValue( ApplicationGlobal.usersFake)
                            states.postValue(ListUsersScreenStates.ListContentState)
                        }else{
                            Log.e(TAG, "users from db!! ")
                            ListUsersScreenStates.ListContentState.users =  users.toList()
                            liveDataUsers.postValue(users.toList())
                            states.postValue(ListUsersScreenStates.ListContentState)
                        }
                    }
                }
            }
            ListScreenEvents.GetListUpdatedDataEvent -> {
                Log.e(TAG, "GetListUpdatedDataEvent")
                coroutineScope.launch {
                    dbManager.getAllUsersFlow().collect {
                        ListUsersScreenStates.UpdateListState.users = it.toList() // <<<== this is the fix for update list
                        liveDataUsers.postValue(it.toList())
                        states.postValue(ListUsersScreenStates.UpdateListState)   // <<<== this is the fix for update list
                    }
                }
            }
        }
    }

    sealed class ListUsersScreenStates {

        object ListContentState : ListUsersScreenStates() {
            var users: List<User>? = null
        }

        object LoadingState : ListUsersScreenStates() {
            var msg: String = "Loading list User"
        }

        object ErrorState : ListUsersScreenStates() {
            var msg: String = "Error Upload list User"
        }

        object UpdateListState : ListUsersScreenStates() {
            var users: List<User>? = null
        }
    }

    sealed class ListScreenEvents {
        object GetCurrentListUsersEvent : ListScreenEvents()
        object GetListUpdatedDataEvent : ListScreenEvents()
    }

    override fun onCleared() {
        Log.e(TAG, "onCleared")
        super.onCleared()
        coroutineScope.cancel()
        dbManager.getDatabaseSingleton().close()
    }
}