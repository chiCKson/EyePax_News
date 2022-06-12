package com.chickson.eyepaxnews.activites.newsdetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chickson.eyepaxnews.models.Article
import com.chickson.eyepaxnews.ui.theme.EyePaxNewsTheme
import com.google.gson.Gson

class NewsDetailActivity : ComponentActivity() {
    lateinit var article: Article
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        article = Gson().fromJson(intent.getStringExtra("article"),Article::class.java)
        setContent {
            EyePaxNewsTheme {
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