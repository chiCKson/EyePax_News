package com.chickson.eyepaxnews.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chickson.eyepaxnews.activites.main.MainViewModel

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