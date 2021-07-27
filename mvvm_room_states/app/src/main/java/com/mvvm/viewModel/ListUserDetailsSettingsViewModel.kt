package com.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.model.User
import com.mvvm.repository.DbManager
import com.mvvm.serviceImpl.ApiFilmServiceImpl
import com.mvvm.utils.FilmApplicationGlobal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ListUserDetailsSettingsViewModel
@Inject constructor(
    private val apiFilmServiceImpl: ApiFilmServiceImpl,
    private val dbManager: DbManager,
) : ViewModel() {

    private val TAG: String = ListUserDetailsSettingsViewModel::class.java.simpleName
    private val dispatchers: CoroutineContext = Dispatchers.IO
    private var coroutineScope: CoroutineScope = CoroutineScope(dispatchers + SupervisorJob())
    private var liveDataUsers: MutableLiveData<List<User>> = MutableLiveData<List<User>>()
    var states: MutableLiveData<ListUsersScreenStates> = MutableLiveData<ListUsersScreenStates>()

    init {
        Log.e(TAG, "INJECTION VALUE dbInMemory != null  ${dbManager != null}")
        //dbManager.deleteAllRowsAllTables()
    }
//
//    fun getAllUsers(): LiveData<List<User>>? {
//        if (apiFilmServiceImpl != null) {
//            if (liveDataUsers == null) {
//                coroutineScope.launch {
//                    val users = dbManager.getAllUsers()
//                    if (!users.isNullOrEmpty()) {
//                        Log.e(TAG, "load  users from db...")
//                        liveDataUsers.postValue(users)
//                        users.forEachIndexed { index, user ->
//                            Log.e(TAG, "user.id >>>  ${user.id}")
//                        }
//                    } else {
//                        Log.e(TAG, "load  users from fake list...")
//                        FilmApplicationGlobal.usersFake.forEachIndexed { _, user ->
//                            dbManager.insertUser(user)
//                        }
//                        liveDataUsers.postValue(FilmApplicationGlobal.usersFake)
//                    }
//                }
//            }
//        }
//        return liveDataUsers
//    }

    fun updateListLiveState(event: ListScreenEvents) {
        when (event) {
            ListScreenEvents.GetCurrentListUsersEvent -> {
                Log.e(TAG, "GetCurrentListUsersEvent")
                coroutineScope.launch {
                    val users = dbManager.getAllUsers()
                    ListUsersScreenStates.ListContentState.users = users
                }
                states.postValue(ListUsersScreenStates.ListContentState)
            }
            ListScreenEvents.GetListUpdatedDataEvent -> {
                Log.e(TAG, "GetListUpdatedDataEvent")
                coroutineScope.launch {
                    val users = dbManager.getAllUsers()
                    ListUsersScreenStates.UpdateListState.users = users
                }
                states.postValue(ListUsersScreenStates.UpdateListState)
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
        dbManager.db!!.close()
    }
}