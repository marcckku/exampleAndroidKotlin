package com.dataflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.dataflow.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData

/**
 *  MVVM
 *  VIEWMODEL
 *  STATES
 *  EVENTS
 * */

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtExample.text = "STATE TWO"

        /// QUESTO SEGMENTO SERVE PER ASCOLTARE I POSSIBILI EVENTI E QUINDI MOSTRARE ALL'UTENTE
        /// LA INTERFACCIA DOVUTA
        mainViewModel.states.observe(this, { state ->
            when(state){
                MainScreenStates.Content ->  binding.txtExample.text = "STATE CONTENT"
                MainScreenStates.Error   ->  binding.txtExample.text = "STATE ERROR"
                MainScreenStates.Loading ->  binding.txtExample.text = "STATE LOADING"
            }
        })

        ///Create Event to get data
        // Viene eseguito per prima del segmento codice  mainViewModel.states.observe ...
        mainViewModel.send(MainScreenEvents.OnReady)


    }
}


class MainViewModel : ViewModel(){

    var states  : MutableLiveData<MainScreenStates> = MutableLiveData<MainScreenStates>()

    fun send(event : MainScreenEvents) {
        when(event){
            MainScreenEvents.OnReady -> {
                states.postValue(MainScreenStates.Content)
            }
        }
    }
}



sealed class MainScreenStates{
    object Content : MainScreenStates ()
    object Loading : MainScreenStates ()
    object Error   : MainScreenStates ()
}



sealed class MainScreenEvents{
    object OnReady : MainScreenEvents ()
}