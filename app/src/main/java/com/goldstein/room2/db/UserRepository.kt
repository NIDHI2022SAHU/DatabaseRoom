package com.goldstein.room2.db

import com.goldstein.room2.model.User

class UserRepository(
    private val userDao: UserDao
) {

    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun getAllUsers(): List<User>{
        return userDao.getAllUsers()
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun deleteAll() {
        userDao.deleteAll()
    }

}