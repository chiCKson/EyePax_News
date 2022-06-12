package com.chickson.eyepaxnews.activites.newsdetails

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chickson.eyepaxnews.R
import com.chickson.eyepaxnews.activites.main.MainActivity
import com.chickson.eyepaxnews.models.Article
import com.chickson.eyepaxnews.ui.theme.EyePaxNewsTheme
import com.chickson.eyepaxnews.views.TopBar
import com.google.gson.Gson

class NewsDetailActivity : ComponentActivity() {
    lateinit var article: Article
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        article = Gson().fromJson(intent.getStringExtra("article"),Article::class.java)
        setContent {
            EyePaxNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NewsDetailView(article = article)
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    EyePaxNewsTheme {
        NewsDetailView(Article())
    }
}