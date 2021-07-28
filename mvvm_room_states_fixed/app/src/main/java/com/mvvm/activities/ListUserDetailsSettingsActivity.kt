package com.mvvm.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.adapter.AdapterUserDetailsSetting
import com.mvvm.databinding.ListUserBinding
import com.mvvm.model.User
import com.mvvm.utils.ApplicationGlobal
import com.mvvm.viewModel.ListUserDetailsSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListUserDetailsSettingsActivity : AppCompatActivity() {

    private val TAG: String = ListUserDetailsSettingsActivity::class.java.simpleName
    lateinit var binding: ListUserBinding
    private lateinit var applicationGlobal: ApplicationGlobal
    private lateinit var adapter : AdapterUserDetailsSetting
    val viewModel: ListUserDetailsSettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applicationGlobal = applicationContext as ApplicationGlobal

        ///Con liveDataUsers evito codice verboso o boilerplate
        viewModel.liveDataUsers.observe(this, { users ->
            Log.e(TAG, " STATE :: ListContentState")
            setAdapter(users)
        })

///////////////// BOILER PLATE - NON HO BISOGNO DI TUTTI GLI STATI AL MOMENTO ////////////////////
//        viewModel.states.observe(this, { state ->
//            binding.profileError.text = ""
//            binding.profileError.visibility = View.GONE
//            when (state) {
//                ListUserDetailsSettingsViewModel.ListUsersScreenStates.ListContentState -> {
//                    val users =
//                        ListUserDetailsSettingsViewModel.ListUsersScreenStates.ListContentState.users
//                    Log.e(TAG, " STATE :: ListContentState")
//                    setAdapter(users!!)
//                }
//                ListUserDetailsSettingsViewModel.ListUsersScreenStates.LoadingState -> {
//
//                }
//                ListUserDetailsSettingsViewModel.ListUsersScreenStates.UpdateListState -> {
//                    val users =
//                        ListUserDetailsSettingsViewModel.ListUsersScreenStates.UpdateListState.users
//                    Log.e(TAG, " STATE :: UpdateListState")
//                    setAdapter(users!!)
//                }
//                ListUserDetailsSettingsViewModel.ListUsersScreenStates.ErrorState -> {
//                    binding.profileError.visibility = View.VISIBLE
//                    binding.profileError.text =
//                        ListUserDetailsSettingsViewModel.ListUsersScreenStates.ErrorState.msg
//                }
//            }
//        })
/////////////////////// BOILER PLATE ///////////////////////////

        viewModel.updateListLiveState(ListUserDetailsSettingsViewModel.ListScreenEvents.GetCurrentListUsersEvent)
    }


    private fun setAdapter(users : List<User>) {
        adapter =  AdapterUserDetailsSetting(users)
        binding.userDetailsSettingRecyclerView.adapter  = adapter
        binding.userDetailsSettingRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.userDetailsSettingRecyclerView.setHasFixedSize(true)
    }
}