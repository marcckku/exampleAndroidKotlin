package com.mvvm.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.adapter.AdapterUserDetailsSetting
import com.mvvm.databinding.ListUserBinding
import com.mvvm.utils.FilmApplicationGlobal
import com.mvvm.viewModel.ListUserDetailsSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListUserDetailsSettingsActivity : AppCompatActivity() {

    private val TAG: String = ListUserDetailsSettingsActivity::class.java.simpleName
    lateinit var binding: ListUserBinding
    private lateinit var filmApplicationGlobal: FilmApplicationGlobal
    val viewModel: ListUserDetailsSettingsViewModel by viewModels() // in kotlin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        filmApplicationGlobal = applicationContext as FilmApplicationGlobal
        binding.userDetailsSettingRecyclerView.layoutManager = LinearLayoutManager(this)
//
//        viewModel.getAllUsers()!!.observe(this,{ users ->
//            Log.e(TAG, " viewModel.getAllUsers()!!.observe ")
//            binding.userDetailsSettingRecyclerView.adapter =  AdapterUserDetailsSetting(users)
//        })

        viewModel.states.observe(this, { state ->
            binding.profileError.text = ""
            binding.profileError.visibility = View.GONE
            when (state) {
                ListUserDetailsSettingsViewModel.ListUsersScreenStates.ListContentState -> {
                    val users =
                        ListUserDetailsSettingsViewModel.ListUsersScreenStates.ListContentState.users
                    Log.e(TAG, " STATE :: ListContentState")
                    binding.userDetailsSettingRecyclerView.adapter =
                        AdapterUserDetailsSetting(users!!)
                }
                ListUserDetailsSettingsViewModel.ListUsersScreenStates.LoadingState -> {

                }
                ListUserDetailsSettingsViewModel.ListUsersScreenStates.UpdateListState -> {
                    val users =
                        ListUserDetailsSettingsViewModel.ListUsersScreenStates.UpdateListState.users
                    Log.e(TAG, " STATE :: UpdateListState")
                    binding.userDetailsSettingRecyclerView.adapter =
                        AdapterUserDetailsSetting(users!!)
                }
                ListUserDetailsSettingsViewModel.ListUsersScreenStates.ErrorState -> {
                    binding.profileError.visibility = View.VISIBLE
                    binding.profileError.text =
                        ListUserDetailsSettingsViewModel.ListUsersScreenStates.ErrorState.msg
                }
            }
        })
        viewModel.updateListLiveState(ListUserDetailsSettingsViewModel.ListScreenEvents.GetCurrentListUsersEvent)
    }
}