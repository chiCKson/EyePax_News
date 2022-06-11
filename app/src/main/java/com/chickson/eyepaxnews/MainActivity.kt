package com.chickson.eyepaxnews

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chickson.eyepaxnews.models.Article
import com.chickson.eyepaxnews.network.Config
import com.chickson.eyepaxnews.network.services.NewsApi
import com.chickson.eyepaxnews.repositories.NewsRepository
import com.chickson.eyepaxnews.ui.theme.EyePaxNewsTheme
import com.chickson.eyepaxnews.ui.theme.TransGrey
import dagger.hilt.android.AndroidEntryPoint
import com.chickson.eyepaxnews.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTopHeadlines()
        viewModel.selectedCategory.value = resources.getStringArray(R.array.news_categories).first()
        viewModel.getNewsByCategories()
        setContent {
            EyePaxNewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView(viewModel = viewModel)
                }
            }
        }
    }
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainView(viewModel: MainViewModel) {
    val context = LocalContext.current
    Scaffold(
        topBar =  {
            Text(text = "Search")
        },
        content = {
            Column(  modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
            ) {
                Text(
                    text = "Breaking News",
                    style = MaterialTheme.typography.h1,
                    color = Color.Black
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    items(viewModel.breakingNews.value){ item->
                        BreakingNewsItem(article = item)
                    }
                }

                LazyRow(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    items(context.resources.getStringArray(R.array.news_categories)){ category->
                        Button(onClick = {
                         viewModel.onSelectedCategoryChange(category = category)
                        },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = if (category.equals(viewModel.selectedCategory.value)) MaterialTheme.colors.primary else Color.White),
                            border = BorderStroke(1.dp,if (category.equals(viewModel.selectedCategory.value)) MaterialTheme.colors.primary else Color.Black)
                        ) {
                            Text(text = category, color = if (category.equals(viewModel.selectedCategory.value)) MaterialTheme.colors.onPrimary else Color.Black)
                        }

                    }
                }
                
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ){
                    items(viewModel.news.value){ news->
                        NewsItem(article = news)
                    }
                }
            }
        },
        bottomBar = {
            Text(text = "Bottom Bar")
        }
    )
}

@Composable
fun BreakingNewsItem(article: Article){
    val configuration = LocalConfiguration.current
    Card(
        modifier = Modifier
            .width(configuration.screenWidthDp.dp)
            .clickable { },
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

@Composable
fun NewsItem(article: Article) {
    val configuration = LocalConfiguration.current
    Card(
        modifier = Modifier
            .width(configuration.screenWidthDp.dp)
            .clickable { },
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
                modifier = Modifier.height(150.dp).padding(15.dp),
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


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EyePaxNewsTheme {
        MainView(MainViewModel(NewsRepository(NewsApi(Config()))))
    }
}