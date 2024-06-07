package com.bytehala.noteiap.note.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.bytehala.noteiap.NoteTitleItem
import com.bytehala.noteiap.database.AppDatabase
import com.bytehala.noteiap.note.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val noteDao = db.noteDao()
    val notes: List<Note> by noteDao.getAllNotes().observeAsState(emptyList())

    var newNote by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = newNote,
                onValueChange = { newNote = it },
                label = { Text("Quick Note") },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                )
            )
            IconButton(
                onClick = {
                    if (newNote.isNotBlank()) {
                        coroutineScope.launch(Dispatchers.IO) {
                            val title = newNote.split("\n").first()
                            val note = Note(title = title, content = newNote)
                            noteDao.insert(note)
                            newNote = ""
                        }
                    }
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Save Note")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(notes) { note ->
                NoteTitleItem(name = note.content)
                Spacer(modifier = Modifier.height(24.dp)) // Add spacing between items
            }
        }
    }
}