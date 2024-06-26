package com.mohaberabi.testingexample.core.data.source.local.database.mappers

import com.mohaberabi.testingexample.core.data.source.local.database.entity.NoteEntity
import com.mohaberabi.testingexample.core.domain.model.NoteModel


fun NoteEntity.toNoteModel(): NoteModel = NoteModel(
    id = id ?: 0L,
    title = title,
    body = body,
    imageUrl = imageUrl,
    addedAtMillis = addedAtMillis,
)


fun NoteModel.toNoteEntity(
    noteId: Long? = null
): NoteEntity = NoteEntity(
    id = noteId,
    title = title,
    body = body,
    imageUrl = imageUrl,
    addedAtMillis = addedAtMillis
)