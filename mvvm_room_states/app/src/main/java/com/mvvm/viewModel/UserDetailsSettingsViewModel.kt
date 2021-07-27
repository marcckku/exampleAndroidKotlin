package com.mvvm.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.model.User
import com.mvvm.repository.DbManager
import com.mvvm.serviceImpl.ApiFilmServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@HiltViewModel
class UserDetailsSettingsViewModel
@Inject constructor(
    private val apiFilmServiceImpl: ApiFilmServiceImpl,
    private val dbManager: DbManager,
) : ViewModel() {

    private val TAG: String = UserDetailsSettingsViewModel::class.java.simpleName
    private val dispatchers: CoroutineContext = Dispatchers.IO
    private var coroutineScope: CoroutineScope = CoroutineScope(dispatchers + SupervisorJob())
    var states: MutableLiveData<UserDetailsScreenStates> = MutableLiveData<UserDetailsScreenStates>()

    init {
        Log.e(TAG, "INJECTION VALUE dbInMemory != null  ${dbManager != null}")
    }

    fun updateUserDetailsLiveState(event: UserDetailsScreenEvents, userToUpdate: User?, id: String?) {
        when (event) {
            is UserDetailsScreenEvents.GetDataEvent -> {
                coroutineScope.launch {
                    UserDetailsScreenStates.ContentState.user = dbManager.getUserById(id!!)
                    states.postValue(UserDetailsScreenStates.ContentState)
                }
            }
            is UserDetailsScreenEvents.InitUpdateEvent -> {
                coroutineScope.launch {
                    UserDetailsScreenStates.UpdateState.user = dbManager.updateUser(userToUpdate!!)
                    states.postValue(UserDetailsScreenStates.UpdateState)
                }
            }
        }
    }

    sealed class UserDetailsScreenStates {

        object ContentState : UserDetailsScreenStates() {
            var user: User? = null
        }

        object LoadingState : UserDetailsScreenStates() {
            var msg: String = "Loading info User"
        }

        object ErrorState : UserDetailsScreenStates() {
            var msg: String = "Errore Upload info User"
        }

        object UpdateState : UserDetailsScreenStates() {
            var user: User? = null
        }
    }

    sealed class UserDetailsScreenEvents {
        object GetDataEvent : UserDetailsScreenEvents()
        object InitUpdateEvent : UserDetailsScreenEvents()
    }


    override fun onCleared() {
        Log.e(TAG, "onCleared")
        super.onCleared()
        coroutineScope.cancel()
        dbManager.db!!.close()
    }

}


