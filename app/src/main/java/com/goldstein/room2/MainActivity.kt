package com.goldstein.room2

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.addUser_fab)

        //insert user
        fab.setOnClickListener {
            showDialog(it)
        }

//        viewModel.users.observe(this, Observer {
//
//        })

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

    private fun insertUser() {
        val name = nameTextView.text.toString()
        val email = emailTextView.text.toString()
        val age = ageTextView.text.toString()

        val user = User(name = name, email = email, age = age.toInt())
        viewModel.insertUser(user)

        nameTextView.text.clear()
        emailTextView.text.clear()
        ageTextView.text.clear()
    }

    fun showDialog(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Details")

        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_layout, null)
        val etName = dialogLayout.findViewById<EditText>(R.id.et_name)
        val etEmail = dialogLayout.findViewById<EditText>(R.id.et_email)
        val etAge = dialogLayout.findViewById<EditText>(R.id.et_age)
        builder.setView(dialogLayout)

        builder.setPositiveButton("Submit") { dialogInterface, i ->
            // Handle submit button click
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val age = etAge.text.toString()

            val user = User(name = name, email = email, age = age.toInt())
            viewModel.insertUser(user)

            etName.text.clear()
            etEmail.text.clear()
            etAge.text.clear()
        }

        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            // Handle cancel button click
        }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                loadUsers()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}