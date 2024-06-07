@file:OptIn(ExperimentalMaterial3Api::class)

package com.bytehala.uglynotes.note.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.lifecycleScope
import com.bytehala.uglynotes.database.AppDatabase
import com.bytehala.uglynotes.note.model.Note
import kotlinx.coroutines.launch

class EditNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val note = intent.getSerializableExtra("note") as? Note
        val db = AppDatabase.getDatabase(this)
        val noteDao = db.noteDao()

        setContent {
            note?.let {
                EditNoteScreen(note = it) { updatedNote ->
                    lifecycleScope.launch {
                        noteDao.update(updatedNote)
                        finish()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditNoteScreen(note: Note, onSave: (Note) -> Unit) {
    var title by remember { mutableStateOf(TextFieldValue(note.title)) }
    var content by remember { mutableStateOf(TextFieldValue(note.content)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Note", fontSize = 5.em) },
                actions = {
                    IconButton(onClick = {
                        val updatedNote = note.copy(
                            title = title.text,
                            content = content.text,
                            updatedTimestamp = System.currentTimeMillis()
                        )
                        onSave(updatedNote)
                    }, modifier = Modifier.border(1.dp, Color.Gray, CircleShape)) {
                        Icon(Icons.Default.Done, contentDescription = "Save")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp)) {
//            TextField(
//                value = title,
//                onValueChange = { title = it },
//                modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth(),
//                textStyle = TextStyle(fontSize = 4.em),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    disabledContainerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.LightGray,
//                    unfocusedIndicatorColor = Color.LightGray,
//                    disabledIndicatorColor = Color.LightGray,
//                ),
//                maxLines = 1
//            )
            TextField(
                value = content,
                onValueChange = { content = it },
                modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth(),
                textStyle = TextStyle(fontSize = 3.em),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray,
                    disabledIndicatorColor = Color.LightGray,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditNoteScreenPreview() {
    val note = Note(
        id = 1,
        content = "This is a sample note",
        title = "Sample Title",
        createdTimestamp = System.currentTimeMillis(),
        updatedTimestamp = System.currentTimeMillis()
    )
    EditNoteScreen(note = note) {
        // Preview doesn't handle saving
    }
}