package com.goldstein.room2.db

import androidx.room.*
import com.goldstein.room2.model.User

@Dao
interface UserDao {

    //create user
    @Insert
    suspend fun insertUser(user: User)

    //read users
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    //update user
    @Update
    suspend fun updateUser(user: User)



    //delete user
    @Delete
    suspend fun deleteUser(user: User)

    //deleteAll
    @Query("DELETE FROM users")
    suspend fun deleteAll()
}