package com.mohaberabi.testingexample.core.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "note"
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val body: String,
    val imageUrl: String,
    val addedAtMillis: Long
)
