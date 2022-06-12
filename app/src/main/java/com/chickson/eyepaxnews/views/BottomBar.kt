package com.chickson.eyepaxnews.views

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chickson.eyepaxnews.R
import com.chickson.eyepaxnews.activites.login.LoginActivity
import com.chickson.eyepaxnews.prefs

@Composable
fun BottomBar(){
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    Row(modifier = Modifier
        .fillMaxWidth().height(100.dp)
        .padding(horizontal = 50.dp).padding(bottom = 20.dp)
        .clip(RoundedCornerShape(50.dp))
        .background(Color.White)
        .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly


    ) {
        IconButton(onClick = { }) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
        }
        IconButton(onClick = { }) {
            Image(
                painter = painterResource(id = R.drawable.favourite),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
        }
        IconButton(onClick = { }) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
        }

        IconButton(onClick = {
            prefs.user = null
            context.startActivity(Intent(context, LoginActivity::class.java))
            activity.finish()
        }) {
            Image(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
        }
    }
}