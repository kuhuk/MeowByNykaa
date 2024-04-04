package com.meowbynykaa.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.meowbynykaa.app.data.Cat
import com.meowbynykaa.app.ui.theme.MeowByNykaaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeowByNykaaTheme {
                Column {
                    TopAppBar(
                        title = { Text(text = "Meows") },
                        colors = TopAppBarColors(Color.DarkGray, Color.White, Color.Yellow, Color.White, Color.Yellow)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        val viewModel: MainViewModel = viewModel()
                        val cat = viewModel.state.value.catList
                        val isLoading = viewModel.state.value.isLoading

                        cat?.let {
                            CatComposeList(cats = cat)
                        }

                        if (isLoading) {
                            Text(text = "Meows are loading...")
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CatComposeList(
        modifier: Modifier = Modifier, cats: List<Cat>) {
        LazyColumn(modifier = modifier) {
            items(cats.size) { data ->
                CatListItem(cat = cats[data])
            }
        }
    }

    @Composable
    fun CatListItem(cat: Cat) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = cat.url,
                contentDescription = "Cats",
                modifier = Modifier
                    .height(250.dp)
                    .width(250.dp),
                contentScale = ContentScale.FillBounds)

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "This is a cat. It meows.")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}