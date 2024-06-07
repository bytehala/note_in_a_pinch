package com.bytehala.noteiap.note.view

import InkTextField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.bytehala.noteiap.database.AppDatabase
import com.bytehala.noteiap.note.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NotesScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val noteDao = db.noteDao()
    val notes: List<Note> by noteDao.getAllNotes().observeAsState(emptyList())

    var newNote by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = newNote,
            onValueChange = { newNote = it },
            label = { Text("New Note") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (newNote.text.isNotBlank()) {
                coroutineScope.launch(Dispatchers.IO) {
                    val note = Note(content = newNote.text)
                    noteDao.insert(note)
                    newNote = TextFieldValue("") // Reset the state after insertion
                }
            }
        }) {
            Text("Add Note")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(notes) { note ->
                Text(text = note.content)
                Divider()
            }
        }
    }
}