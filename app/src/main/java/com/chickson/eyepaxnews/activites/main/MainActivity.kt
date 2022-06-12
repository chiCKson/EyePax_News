package com.chickson.eyepaxnews.activites.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chickson.eyepaxnews.R
import com.chickson.eyepaxnews.network.Config
import com.chickson.eyepaxnews.network.services.NewsApi
import com.chickson.eyepaxnews.repositories.NewsRepository
import com.chickson.eyepaxnews.ui.theme.EyePaxNewsTheme
import dagger.hilt.android.AndroidEntryPoint

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EyePaxNewsTheme {
        MainView(MainViewModel(NewsRepository(NewsApi(Config()))))
    }
}