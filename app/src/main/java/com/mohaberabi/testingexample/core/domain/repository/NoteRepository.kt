package com.mohaberabi.testingexample.core.domain.repository

import com.mohaberabi.testingexample.core.domain.model.NoteModel

interface NoteRepository {


    suspend fun addNote(note: NoteModel): Result<Unit>

    suspend fun deleteNote(id: Long): Result<Unit>
    suspend fun getAllNotes(): Result<List<NoteModel>>
}