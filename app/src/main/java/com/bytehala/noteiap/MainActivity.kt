package com.bytehala.noteiap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.bytehala.noteiap.note.view.NotesScreen
import com.bytehala.noteiap.ui.custom.SquareButton
import com.bytehala.noteiap.ui.theme.NoteInAPinchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteInAPinchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(listOf("Android", "Jetpack Compose", "Kotlin"))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(names: List<String>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ugly Notes", fontSize = 5.em)
                }
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize())
            {
                Spacer(modifier = Modifier.height(8.dp))
                NotesScreen()
            }
            AddButton(modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp))
            PagingButtons(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp))
        }
    }
}

@Composable
fun AddButton(modifier: Modifier) {
    Row(
        modifier = modifier
    ) {
        SquareButton(onClick = { /* Handle click */ }) {
            Text("+", fontSize = 4.em, overflow = TextOverflow.Visible)
        }
    }
}

@Composable
fun PagingButtons(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        SquareButton(onClick = { /* Handle click */ }) {
            Text("<", fontSize = 4.em, overflow = TextOverflow.Visible)
        }
        Spacer(modifier = Modifier.width(8.dp))
        SquareButton(onClick = { /* Handle click */ }) {
            Text(">", fontSize = 4.em, overflow = TextOverflow.Visible)
        }
    }
}

@Composable
fun NoteTitleItem(name: String) {
    Text(text = "$name!", fontSize = 4.em, maxLines = 1, overflow = TextOverflow.Ellipsis)
}

@Preview(showBackground = true, widthDp = 270, heightDp = 540)
@Composable
fun DefaultPreview() {
    NoteInAPinchTheme {
        MainContent(names = listOf("Android", "Jetpack Compose Bla bla bla", "Kotlin"))
    }
}