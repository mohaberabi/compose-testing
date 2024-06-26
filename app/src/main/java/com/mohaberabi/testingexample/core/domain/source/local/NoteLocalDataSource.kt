package com.mohaberabi.testingexample.core.domain.source.local

import com.mohaberabi.testingexample.core.domain.model.NoteModel

interface NoteLocalDataSource {


    suspend fun addNote(note: NoteModel)

    suspend fun deleteNote(id: Long)
    suspend fun getAllNotes(): List<NoteModel>
}