package com.bytehala.noteiap.note.view

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.content.ContextCompat.startActivity
import com.bytehala.noteiap.database.AppDatabase
import com.bytehala.noteiap.note.model.Note
import com.bytehala.noteiap.note.model.NoteDao
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NotesScreen(notes: List<Note> = emptyList(), noteDao: NoteDao?) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val swKeyboard = LocalSoftwareKeyboardController.current
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
                textStyle = TextStyle(fontSize = 3.em),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                )
            )
            if (newNote.isNotBlank() || isPreview) {
                IconButton(
                    onClick = {
                        if (newNote.isNotBlank()) {
                            coroutineScope.launch {
                                val title = newNote.split("\n").first()
                                val note = Note(title = title, content = newNote)
                                noteDao?.insert(note)
                                newNote = ""
                                swKeyboard?.hide()
                            }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .border(1.dp, Color.Gray, CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Save Note")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(notes) { note ->
                NoteTitleItem(title = note.title, onClick = {
                    val intent = Intent(context, EditNoteActivity::class.java).apply {
                        putExtra("note", note)
                    }
                    context.startActivity(intent)
                } )
            }
        }
    }
}

@Composable
fun NoteTitleItem(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .height(IntrinsicSize.Min)
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            fontSize = 4.em,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 12.dp)
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