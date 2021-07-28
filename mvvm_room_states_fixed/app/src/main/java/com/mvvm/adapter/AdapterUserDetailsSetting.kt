package com.mvvm.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.activities.UserDetailsSettingActivity
import com.mvvm.databinding.UserProfileRowBinding
import com.mvvm.model.User

class AdapterUserDetailsSetting(private val users: List<User>) :
    RecyclerView.Adapter<AdapterUserDetailsSetting.UserViewHolder?>() {

    private val TAG: String = AdapterUserDetailsSetting::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = UserProfileRowBinding.inflate(inflater, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class UserViewHolder(private val userProfileRowBinding: UserProfileRowBinding) :
        RecyclerView.ViewHolder(userProfileRowBinding.root) {
        fun bind(user: User) {
            with(itemView) {
                //userProfileRowBinding.profileDescription.setValue(user.description!! )
                userProfileRowBinding.profileEmail.text = user.email
                userProfileRowBinding.profileRole.text = user.role
                userProfileRowBinding.userProfile.setOnClickListener {
                    val intent = Intent(
                        itemView.context,
                        UserDetailsSettingActivity::class.java
                    )
                    intent.putExtra("user.id", user.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}