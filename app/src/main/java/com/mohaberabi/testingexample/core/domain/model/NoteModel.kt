package com.mohaberabi.testingexample.core.domain.model

data class NoteModel(
    val id: Long,
    val title: String,
    val body: String,
    val imageUrl: String,
    val addedAtMillis: Long,
)
