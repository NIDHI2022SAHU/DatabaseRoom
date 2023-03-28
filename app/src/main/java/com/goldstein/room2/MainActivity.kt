package com.goldstein.room2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goldstein.room2.db.UserDatabase
import com.goldstein.room2.db.UserRepository
import com.goldstein.room2.model.User
import com.goldstein.room2.viewmodel.UserViewModel
import com.goldstein.room2.viewmodel.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var nameTextView: EditText
    private lateinit var emailTextView: EditText
    private lateinit var ageTextView: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository =
            UserRepository(UserDatabase.getDatabaseInstance(applicationContext).userDao())
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
        nameTextView = findViewById(R.id.username_tv)
        emailTextView = findViewById(R.id.email_tv)
        ageTextView = findViewById(R.id.age_tv)
        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.addUser_fab)



        //insert user
        fab.setOnClickListener {
            insertuser()
            loadUsers()

        }

    }

    private fun initRecyclerview() {
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun loadUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val users = viewModel.getAllUsers()
            lifecycleScope.launch(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "Total Size ${users.size}",
                    Toast.LENGTH_LONG
                ).show()
                initRecyclerview()
                val adapter = UserAdapter(users)
                recyclerView.adapter = adapter
            }
        }
    }

    private fun insertuser() {
        val name = nameTextView.text.toString()
        val email = emailTextView.text.toString()
        val age = ageTextView.text.toString()

        val user = User(name = name, email = email, age = age.toInt())
        viewModel.insertUser(user)

        nameTextView.text.clear()
        emailTextView.text.clear()
        ageTextView.text.clear()
    }
}