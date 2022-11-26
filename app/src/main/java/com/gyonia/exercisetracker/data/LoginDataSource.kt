package com.gyonia.exercisetracker.data

import com.gyonia.exercisetracker.ExerciseApplication
import com.gyonia.exercisetracker.data.model.LoggedInUser
import com.gyonia.exercisetracker.database.RoomLogin
import com.gyonia.exercisetracker.repository.LoginRepo
import kotlinx.coroutines.runBlocking
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val dao = ExerciseApplication.loginDatabase.loginDao()
            val repo = LoginRepo(dao)
            if(repo.checkUsernameExists(username)) {
                val id = repo.getIdFromLoginInfo(username, password)
                if(id != null) {
                    ExerciseApplication.userId = id.toString()
                    return Result.Success(LoggedInUser(id.toString(), username))
                }
            } else {
                runBlocking { repo.insertLogin(RoomLogin(0, username, password)) }
                val id = repo.getIdFromLoginInfo(username, password)
                if(id != null) {
                    ExerciseApplication.userId = id.toString()
                    return Result.Success(LoggedInUser(id.toString(), username))
                } else  {
                    return Result.Error(IOException("Error logging in with new credentials"))
                }
            }
/*
            if (dao.checkUsernameExists(username)) {
                var id = dao.getIdFromLoginInfo(username, password)
                if (id != null) {
                    ExerciseApplication.userId = id.toString()
                    return Result.Success(LoggedInUser(id.toString(), username))
                } else  {
                    return Result.Error(IOException("Incorrect password"))
                }
            } else {
                dao.insertLogin(com.gyonia.exercisetracker.database.RoomLogin(0, username, password))
                var id = dao.getIdFromLoginInfo(username, password)
                if (id != null) {
                    ExerciseApplication.userId = id.toString()
                    return Result.Success(LoggedInUser(id.toString(), username))
                } else  {
                    return Result.Error(IOException("Error logging in with new credentials"))
                }
            }*/

            /*
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)*/
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
        return Result.Error(IOException("Error logging in"))
    }

    fun logout() {
        // TODO: revoke authentication
        return
    }
}