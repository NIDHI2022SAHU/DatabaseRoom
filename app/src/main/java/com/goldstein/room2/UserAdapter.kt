package com.goldstein.room2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.goldstein.room2.model.User

class UserAdapter(
    private val users: List<User>
) : RecyclerView.Adapter<UserAdapter.UserViewModel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewModel {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_layout, parent, false)
        return UserViewModel(view)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewModel, position: Int) {
        holder.bind(users[position])
    }

    inner class UserViewModel(userview: View) : ViewHolder(userview) {

        private val username: TextView = userview.findViewById(R.id.userName)
        private val useremail: TextView = userview.findViewById(R.id.userEmail)
        private val userage: TextView = userview.findViewById(R.id.userAge)

        fun bind(user: User) {
            username.text = user.name
            useremail.text = user.email
            userage.text = user.age.toString()
        }
    }
}