package com.bytehala.noteiap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun MainContent(names: List<String>) {
    Box(modifier = Modifier.fillMaxSize()) {
        GreetingList(names = names)
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Button(onClick = { /* TODO: Handle button click */ }) {
                Text("Button 1")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { /* TODO: Handle button click */ }) {
                Text("Button 2")
            }
        }
    }
}

@Composable
fun GreetingList(names: List<String>) {
    LazyColumn {
        items(names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello, $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteInAPinchTheme {
        MainContent(names = listOf("Android", "Jetpack Compose", "Kotlin"))
    }
}