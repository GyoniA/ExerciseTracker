package com.gyonia.exercisetracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LoginDao {
    @Insert
    fun insertLogin(login: RoomLogin)

    @Query("SELECT * FROM login")
    fun getAllLogins(): LiveData<List<RoomLogin>>

    @Update
    fun updateLogin(login: RoomLogin): Int

    @Delete
    fun deleteLogin(login: RoomLogin)

    @Query("SELECT EXISTS(SELECT * FROM login WHERE username = :username)")
    fun checkUsernameExists(username: String): Boolean

    @Query("SELECT * FROM login WHERE username = :username AND password = :password")
    fun getIdFromLoginInfo(username: String, password: String): LiveData<List<RoomLogin>>
}