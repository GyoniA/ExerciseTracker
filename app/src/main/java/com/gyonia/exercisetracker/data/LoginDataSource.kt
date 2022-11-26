package com.gyonia.exercisetracker.data

import android.content.SharedPreferences
import com.gyonia.exercisetracker.ExerciseApplication
import com.gyonia.exercisetracker.data.model.LoggedInUser
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource (private val preferences: SharedPreferences){

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            if(checkUsernameExists(username)) {
                if(checkPassword(username, password)) {
                    ExerciseApplication.userId = username
                    return Result.Success(LoggedInUser(username, username))
                } else {
                    return Result.Error(IOException("Error logging in wrong password"))
                }
            } else {
                addLogin(username, password)
                ExerciseApplication.userId = username
                return Result.Success(LoggedInUser(username, username))
            }
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    private fun addLogin(username: String, password: String) {
        preferences.edit().putString(username, password).apply()
    }

    private fun checkPassword(username: String, password: String) : Boolean {
        preferences.getString(username, null)?.let {
            return it == password
        }
        return false
    }

    private fun checkUsernameExists(username: String): Boolean {
        return preferences.contains(username)
    }

    fun logout() {
        // TODO: revoke authentication
        return
    }
}