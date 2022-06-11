package com.chickson.eyepaxnews.di

import android.content.Context
import androidx.room.Room
import com.chickson.eyepaxnews.system.EyePaxDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): EyePaxDatabase {
        return Room.databaseBuilder(
            context,
            EyePaxDatabase::class.java,
            "eyepax"
        ).build()
    }

}