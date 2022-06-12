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
fun NewsItem(article: Article) {
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
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
    ) {
        Box() {
            AsyncImage(
                model = article.urlToImage ?: "",
                contentDescription = "News article",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .gradientBackground(listOf(TransGrey, TransGrey), angle = 45f)
            )
            Column(
                modifier = Modifier
                    .height(150.dp)
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = article.title ?: "",
                    style = MaterialTheme.typography.body1,
                    color = Color.White)
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Text(
                        text = article.author ?: "",
                        modifier = Modifier.weight(1.0f),
                        style = MaterialTheme.typography.body2,
                        color = Color.White
                    )
                    Text(
                        text = article.publishedAt ?: "",
                        modifier = Modifier.weight(1.0f),
                        style = MaterialTheme.typography.body2,
                        color = Color.White
                    )
                }
            }
        }
    }
}