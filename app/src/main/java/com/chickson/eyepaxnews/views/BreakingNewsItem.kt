package com.chickson.eyepaxnews.views

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chickson.eyepaxnews.activites.newsdetails.NewsDetailActivity
import com.chickson.eyepaxnews.models.Article
import com.chickson.eyepaxnews.ui.theme.TransGrey
import com.chickson.eyepaxnews.util.gradientBackground
import com.google.gson.Gson

@Composable
fun BreakingNewsItem(article: Article){
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .width(configuration.screenWidthDp.dp)
            .clickable {
                val newsDetailActivity = Intent(context, NewsDetailActivity::class.java)
                newsDetailActivity.putExtra("article", Gson().toJson(article))
                context.startActivity(newsDetailActivity)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp
    ) {
        Box() {
            AsyncImage(
                model = article.urlToImage ?: "",
                contentDescription = "News article",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(configuration.screenHeightDp.dp / 3)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(configuration.screenHeightDp.dp / 3)
                    .gradientBackground(listOf(TransGrey, TransGrey), angle = 45f))
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = "by ${article.author ?: ""}",
                    style = MaterialTheme.typography.caption,
                    color = Color.White
                )
                Text(
                    text = article.title ?: "",
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
                Text(
                    text = article.description ?: "",
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
            }
        }

    }
}