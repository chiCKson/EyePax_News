package com.chickson.eyepaxnews.db

import androidx.room.*
import com.chickson.eyepaxnews.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE username LIKE :username   LIMIT 1")
    fun findByUserName(username: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: User)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(users: List<User>)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun clearUsers()
}