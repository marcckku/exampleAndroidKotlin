package com.mvvm.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mvvm.databinding.UserDetailsSettingBinding
import com.mvvm.model.User
import com.mvvm.viewModel.ListUserDetailsSettingsViewModel
import com.mvvm.viewModel.UserDetailsSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserDetailsSettingActivity : AppCompatActivity() {

    private val TAG: String = UserDetailsSettingActivity::class.java.simpleName
    lateinit var binding: UserDetailsSettingBinding
    val viewModel: UserDetailsSettingsViewModel by viewModels()
    val viewModelListUser: ListUserDetailsSettingsViewModel by viewModels()
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserDetailsSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra("user.id")
        Log.e(TAG, "user.id $id")

        if (id.isNullOrEmpty()) {
            throw Exception(" Error Nessun User finded")
        }

        viewModel.states.observe(this, { state ->
            binding.profileError.text = ""
            binding.profileError.visibility = View.GONE
            when (state) {
                UserDetailsSettingsViewModel.UserDetailsScreenStates.ContentState -> {
                    user = UserDetailsSettingsViewModel.UserDetailsScreenStates.ContentState.user!!
                    setTextView(user)
                }
                UserDetailsSettingsViewModel.UserDetailsScreenStates.ErrorState -> {
                    binding.profileError.visibility = View.VISIBLE
                    binding.profileError.text =
                        UserDetailsSettingsViewModel.UserDetailsScreenStates.ErrorState.msg
                }
                UserDetailsSettingsViewModel.UserDetailsScreenStates.LoadingState -> {

                }
                UserDetailsSettingsViewModel.UserDetailsScreenStates.UpdateState -> {
                    user = UserDetailsSettingsViewModel.UserDetailsScreenStates.UpdateState.user!!
                    setTextView(user)
                    ///update list users from here!!!
                    viewModelListUser.updateListLiveState(ListUserDetailsSettingsViewModel.ListScreenEvents.GetListUpdatedDataEvent)
                }
            }
        })

        binding.btnUpdate.setOnClickListener {
            Log.e(TAG, "btnUpdate info User ")
            var email = binding.email.text.toString()
            var pwd   = binding.pwd.text.toString()
            var role  = binding.role.text.toString()
            var desc  = binding.desc.getValue()

            if (email.isNullOrEmpty()) {
                email = user.email!!
            }
            if (pwd.isNullOrEmpty()) {
                pwd = user.pwd!!
            }
            if (role.isNullOrEmpty()) {
                pwd = user.role!!
            }
            if (desc.isNullOrEmpty()) {
                desc = user.description!!
            }

            val userToUpdate =
                User(id = user.id, email = email, role = role, description = desc, pwd = pwd)
            viewModel.updateUserDetailsLiveState(
                UserDetailsSettingsViewModel.UserDetailsScreenEvents.InitUpdateEvent,
                userToUpdate,
                null
            )
        }

        viewModel.updateUserDetailsLiveState(UserDetailsSettingsViewModel.UserDetailsScreenEvents.GetDataEvent, null, id)
    }

    private fun setTextView(user : User){
        clearTextView()
        //to view
        binding.profileEmail.text = user.email?.trim()
        binding.profileRole.text = user.role?.trim()
        binding.profileDescription.setValue(user.description!!.trim())
        /// to update
        binding.email.text!!.append(user.email?.trim()  )
        binding.pwd.text!!  .append(user.pwd?.trim()    )
        binding.role.text!! .append(user.role?.trim()   )
        binding.desc.setValue(user.description?.trim()  )
    }

    private fun clearTextView(){
        //to view
        binding.profileEmail.text = ""
        binding.profileRole.text  = ""
        binding.profileDescription.setValue("")
        /// to update
        binding.email.text!!.clear()
        binding.pwd.text!!.clear()
        binding.role.text!!.clear()
        binding.desc.setValue("")
    }
}