package com.goldstein.room2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.goldstein.room2.model.User
class UserAdapter(
    private var users: List<User>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dialog_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun setUserList(userList: List<User>) {
        users = userList
        notifyDataSetChanged()
    }

    fun getUserAtPosition(position: Int): User {
        return users[position]
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.userName)
        private val emailTextView: TextView = itemView.findViewById(R.id.userEmail)
        private val ageTextView: TextView = itemView.findViewById(R.id.userAge)

        fun bind(user: User) {
            nameTextView.text = user.name
            emailTextView.text = user.email
            ageTextView.text = user.age.toString()
        }
    }
}
