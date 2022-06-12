package com.chickson.eyepaxnews.activites.main

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.chickson.eyepaxnews.R
import com.chickson.eyepaxnews.views.*
import kotlinx.coroutines.launch

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