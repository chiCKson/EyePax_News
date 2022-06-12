package com.chickson.eyepaxnews.activites.newsdetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chickson.eyepaxnews.models.Article
import com.chickson.eyepaxnews.views.TopBar

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

