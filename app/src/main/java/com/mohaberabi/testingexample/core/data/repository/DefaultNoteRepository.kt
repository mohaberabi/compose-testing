package com.mohaberabi.testingexample.core.data.repository

import com.mohaberabi.testingexample.core.domain.model.NoteModel
import com.mohaberabi.testingexample.core.domain.repository.NoteRepository
import com.mohaberabi.testingexample.core.domain.source.local.NoteLocalDataSource
import javax.inject.Inject

class DefaultNoteRepository @Inject constructor(
    private val noteLocalDataSource: NoteLocalDataSource,
) : NoteRepository {
    override suspend fun addNote(note: NoteModel): Result<Unit> {
        return try {
            noteLocalDataSource.addNote(note)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun deleteNote(id: Long): Result<Unit> {
        return try {
            noteLocalDataSource.deleteNote(id)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getAllNotes(): Result<List<NoteModel>> {
        return try {
            val notes = noteLocalDataSource.getAllNotes()
            Result.success(notes)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}