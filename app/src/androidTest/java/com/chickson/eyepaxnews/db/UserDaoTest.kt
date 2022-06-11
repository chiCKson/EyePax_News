package com.chickson.eyepaxnews.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.chickson.eyepaxnews.models.User
import com.chickson.eyepaxnews.system.EyePaxDatabase
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {
    private lateinit var  database: EyePaxDatabase
    private lateinit var dao: UserDao
    val users = listOf<User>(User(username = "erandra",password = "test123"),User(username = "erandraj",password = "test123"))
    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            EyePaxDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.userDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertAll() {
        dao.insertAll(users = users)
        val allUsers =  dao.getAll()
        assertThat(allUsers).contains(users.first())
    }

}