package com.bytehala.noteiap.note.view

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.bytehala.noteiap.database.AppDatabase
import com.bytehala.noteiap.note.model.Note
import com.bytehala.noteiap.note.model.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(notes: List<Note> = emptyList(), noteDao: NoteDao?) {
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
                        coroutineScope.launch {
                            val title = newNote.split("\n").first()
                            val note = Note(title = title, content = newNote)
                            noteDao?.insert(note)
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
                NoteTitleItem(title = note.title, onClick = { /* Open the a ctivity*/} )
//                Spacer(modifier = Modifier.height(24.dp)) // Add spacing between items
            }
        }
    }
}

@Composable
fun NoteTitleItem(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            fontSize = 4.em,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotesScreenPreview() {
    val sampleNotes = listOf(
        Note(
            id = 1,
            title = "Sample Note 1",
            content = "This is the content of sample note 1."
        ),
        Note(
            id = 2,
            title = "Sample Note 2",
            content = "This is the content of sample note 2."
        ),
        Note(
            id = 3,
            title = "Sample Note 3",
            content = "This is the content of sample note 3."
        )
    )

    NotesScreen(notes = sampleNotes, noteDao = null)
}

@Composable
fun NotesScreenWrapper() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val noteDao = db.noteDao()
    val notes: List<Note> by noteDao.getAllNotes().observeAsState(emptyList())

    NotesScreen(notes = notes, noteDao = noteDao)
}