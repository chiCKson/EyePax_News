package com.chickson.eyepaxnews.views

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chickson.eyepaxnews.R
import com.chickson.eyepaxnews.activites.main.MainActivity

//region TopBar View
@Composable
fun TopBar(){
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .padding(horizontal = 20.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.ic_back),
            modifier = Modifier
                .height(30.dp)
                .clickable {
                    val dashboard = Intent(context, MainActivity::class.java)
                    context.startActivity(dashboard)
                    activity?.finish()
                },
            contentDescription = "Back Icon"
        )
    }



}
//endregion