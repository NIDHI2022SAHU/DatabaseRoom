package com.goldstein.room2

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.goldstein.room2.db.UserDatabase
import com.goldstein.room2.model.User
import com.goldstein.room2.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailLayout : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_detail_layout)

                    // Display the user details
                    findViewById<TextView>(R.id.user_name_p).text = user.name
                    findViewById<TextView>(R.id.user_email_p).text = user.email
                    findViewById<TextView>(R.id.user_age_p).text = user.age.toString()

            }
        }

