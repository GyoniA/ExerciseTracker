package com.gyonia.exercisetracker.repository

import com.gyonia.exercisetracker.database.LoginDao
import com.gyonia.exercisetracker.database.RoomLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepo (private val loginDao: LoginDao) {

    fun checkUsernameExists(username: String): Boolean{
        return loginDao.checkUsernameExists(username)
    }

    fun getIdFromLoginInfo(username: String, password: String): Int? {
        return loginDao.getIdFromLoginInfo(username, password)
    }

    suspend fun insertLogin(login: RoomLogin) = withContext(Dispatchers.IO) {
        loginDao.insertLogin(login)
    }
}