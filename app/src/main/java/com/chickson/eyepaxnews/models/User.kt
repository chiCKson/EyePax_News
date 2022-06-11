package com.chickson.eyepaxnews.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User(
    @PrimaryKey
    var username: String,
    var password: String?,
)