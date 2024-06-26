package com.bytehala.uglynotes.note.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")
data class Note (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String,
    @ColumnInfo(defaultValue = "") val title: String = "",
    @ColumnInfo(name = "created_timestamp", defaultValue = "CURRENT_TIMESTAMP") val createdTimestamp: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_timestamp") val updatedTimestamp: Long? = null
) : Serializable