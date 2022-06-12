package com.chickson.eyepaxnews.activites.main

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chickson.eyepaxnews.R
import com.chickson.eyepaxnews.activites.login.LoginActivity
import com.chickson.eyepaxnews.activites.newsdetails.NewsDetailActivity
import com.chickson.eyepaxnews.models.Article
import com.chickson.eyepaxnews.network.Config
import com.chickson.eyepaxnews.network.services.NewsApi
import com.chickson.eyepaxnews.prefs
import com.chickson.eyepaxnews.repositories.NewsRepository
import com.chickson.eyepaxnews.ui.theme.EyePaxNewsTheme
import com.chickson.eyepaxnews.ui.theme.TransGrey
import dagger.hilt.android.AndroidEntryPoint
import com.chickson.eyepaxnews.util.*
import com.google.gson.Gson
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTopHeadlines()
        viewModel.selectedCategory.value = resources.getStringArray(R.array.news_categories).first()
        viewModel.selectedSortBy.value = resources.getStringArray(R.array.sort_by_labels).first()
        viewModel.selectedLangauge.value = "en"
        viewModel.updateFilterBar()
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



@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainView(viewModel: MainViewModel) {
    val bottomSheetScaffoldState =  rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val kc = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    ModalBottomSheetLayout(
        sheetContent = {
            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)) {
                Text("Filter")
                Text("Sort By:")
                LazyRow(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    items(context.resources.getStringArray(R.array.sort_by_labels)){ sortby->
                        Button(onClick = {
                            viewModel.selectedSortBy.value = sortby
                        },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = if (sortby.equals(viewModel.selectedSortBy.value)) MaterialTheme.colors.primary else Color.White),
                            border = BorderStroke(1.dp,if (sortby.equals(viewModel.selectedSortBy.value)) MaterialTheme.colors.primary else Color.Black)
                        ) {
                            Text(text = sortby, color = if  (sortby.equals(viewModel.selectedSortBy.value)) MaterialTheme.colors.onPrimary else Color.Black)
                        }

                    }
                }

                Text("Language:")
                LazyRow(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    items(context.resources.getStringArray(R.array.language)){ lang->
                        Button(onClick = {
                            viewModel.selectedLangauge.value = lang
                        },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = if (lang.equals(viewModel.selectedLangauge.value)) MaterialTheme.colors.primary else Color.White),
                            border = BorderStroke(1.dp,if (lang.equals(viewModel.selectedLangauge.value)) MaterialTheme.colors.primary else Color.Black)
                        ) {
                            Text(text = lang, color = if  (lang.equals(viewModel.selectedLangauge.value)) MaterialTheme.colors.onPrimary else Color.Black)
                        }

                    }
                }
                Button(onClick = {
                    viewModel.updateFilterBar()
                    scope.launch {
                        bottomSheetScaffoldState.hide()
                    }
                    viewModel.searchNews()

                },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor =  MaterialTheme.colors.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(text = "Save", color =  MaterialTheme.colors.onPrimary )
                }
            }
        },
        sheetState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        Scaffold(
            topBar =  {
                SearchBar(viewModel = viewModel)
            },
            content = {
                Column(  modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
                ) {
                    if (viewModel.searched.value){
                        LazyRow(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ){
                            items(viewModel.filterBarLabels.value){ label->
                                Button(onClick = {
                                    scope.launch {
                                        bottomSheetScaffoldState.show()
                                    }
                                    kc?.hide()
                                },
                                    shape = RoundedCornerShape(20.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = if (label == "Filter") MaterialTheme.colors.primary else Color.White),
                                    border = BorderStroke(1.dp,if (label == "Filter") MaterialTheme.colors.primary else Color.Black)
                                ) {
                                    Text(text = label, color = if  (label == "Filter") MaterialTheme.colors.onPrimary else Color.Black)
                                }

                            }
                        }
                    }
                    Text(
                        text = viewModel.topLabelText.value,
                        style = MaterialTheme.typography.h1,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                    if (!viewModel.searchBarSelected.value){

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
                BottomBar()
            }
        )
    }

}

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

@Composable
fun SearchBar(viewModel: MainViewModel){

    var textState by remember {
        mutableStateOf(
            ""
        )
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(0.6f)
                .height(50.dp)
                .background(Color.White)
                .border(0.5.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
                .padding(horizontal = 20.dp)
                .clip(
                    RoundedCornerShape(30.dp)

                ),
            verticalAlignment = Alignment.CenterVertically,

            ) {


            BasicTextField(
                value = textState,
                onValueChange = {
                    textState = it
                    viewModel.searchQuery.value = it
                    viewModel.searchBarSelected.value = true
                    viewModel.topLabelText.value = ""
                },
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (textState.isBlank()) {
                            viewModel.searchBarSelected.value = false
                            viewModel.topLabelText.value = "Breaking News"
                            viewModel.searched.value = false

                            Text(
                                text = "Search News",
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier.fillMaxWidth()

            )

        }
        Button(onClick = {
            if (!viewModel.searched.value){

                viewModel.searchNews()
                viewModel.searched.value = true
            } else {
                viewModel.searched.value = false
                textState = ""
            }


        },
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor =  MaterialTheme.colors.primary),
            ) {
            Icon(
                if(viewModel.searched.value) Icons.Filled.Close else Icons.Filled.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(20.dp)

            )
        }
    }

}

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
            context.startActivity(Intent(context,LoginActivity::class.java))
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EyePaxNewsTheme {
        MainView(MainViewModel(NewsRepository(NewsApi(Config()))))
    }
}