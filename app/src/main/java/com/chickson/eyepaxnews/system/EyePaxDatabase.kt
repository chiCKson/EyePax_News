package com.chickson.eyepaxnews.system

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chickson.eyepaxnews.db.UserDao
import com.chickson.eyepaxnews.models.User

@Database(entities = [User::class], version = 1)
abstract class EyePaxDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}