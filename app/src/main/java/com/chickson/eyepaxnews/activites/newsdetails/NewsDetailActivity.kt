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



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewsDetailView(article: Article) {
    val configuration = LocalConfiguration.current
    Scaffold(
        topBar =  {
          TopBar()
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                item {
                    AsyncImage(
                        model = article.urlToImage ?: "",
                        contentDescription = "News article",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(configuration.screenHeightDp.dp / 3)
                    )
                    Text(
                        text = article.title ?: "",
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.padding(horizontal = 15.dp)
                    )
                    Text(
                        text = article.content ?: "",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(horizontal = 15.dp)
                    )

                    Text(
                        text = "- ${article.author} -",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(horizontal = 15.dp)
                    )

                }
               }
        }
    )
}

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
@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    EyePaxNewsTheme {
        NewsDetailView(Article())
    }
}