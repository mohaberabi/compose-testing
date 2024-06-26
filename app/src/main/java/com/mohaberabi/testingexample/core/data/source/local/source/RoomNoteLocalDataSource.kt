package com.mohaberabi.testingexample.core.data.source.local.source

import com.mohaberabi.testingexample.core.data.source.local.database.dao.NoteDao
import com.mohaberabi.testingexample.core.data.source.local.database.mappers.toNoteEntity
import com.mohaberabi.testingexample.core.data.source.local.database.mappers.toNoteModel
import com.mohaberabi.testingexample.core.domain.model.NoteModel
import com.mohaberabi.testingexample.core.domain.source.local.NoteLocalDataSource
import javax.inject.Inject

class RoomNoteLocalDataSource @Inject constructor(
    private val noteDao: NoteDao,
) : NoteLocalDataSource {
    override suspend fun addNote(note: NoteModel) = noteDao.insertNote(note.toNoteEntity())

    override suspend fun deleteNote(id: Long) = noteDao.deleteNote(id)

    override suspend fun getAllNotes(): List<NoteModel> =
        noteDao.getAllNotes().map { it.toNoteModel() }
}