package com.chickson.eyepaxnews.repositories

import com.chickson.eyepaxnews.db.UserDao
import com.chickson.eyepaxnews.models.NewsResponse
import com.chickson.eyepaxnews.models.User
import com.chickson.eyepaxnews.network.NewsResult
import com.chickson.eyepaxnews.network.services.NewsApi
import com.chickson.eyepaxnews.system.EyePaxDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(private val userSource: EyePaxDatabase)  {


    fun saveUsers(users: List<User>) : Flow<Boolean> = flow {
        try {
            userSource.userDao().insertAll(users = users)
            emit(true)
        } catch (e: Exception){
            emit(false)
        }





    }.flowOn(Dispatchers.IO)

    fun findUserByUserName(username: String) : Flow<User> = flow {
        emit(userSource.userDao().findByUserName(username = username))
    }.flowOn(Dispatchers.IO)
}