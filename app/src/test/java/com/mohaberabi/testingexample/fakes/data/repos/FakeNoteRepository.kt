package com.mohaberabi.testingexample.fakes.data.repos

import com.mohaberabi.testingexample.core.domain.model.NoteModel
import com.mohaberabi.testingexample.core.domain.repository.NoteRepository

class FakeNoteRepository : NoteRepository {


    val exception: Throwable = Exception("FakeNoteLocalDataSourceError")
    private val notes = mutableSetOf<NoteModel>(
        NoteModel(id = 0L, title = "title1", "body1", imageUrl = "image1", addedAtMillis = 0L),
        NoteModel(id = 1L, title = "title2", "body2", imageUrl = "image2", addedAtMillis = 0L),
        NoteModel(id = 2L, title = "title3", "body3", imageUrl = "image3", addedAtMillis = 0L),
        NoteModel(id = 3L, title = "title4", "body4", imageUrl = "image4", addedAtMillis = 0L),
        NoteModel(id = 4L, title = "title5", "body5", imageUrl = "image5", addedAtMillis = 0L),
    )

    val notesList = notes.toList()
    var returnFailure: Boolean = false

    var addNoteCallCount = 0
        private set
    var deleteNoteCallsCount = 0
        private set
    var getAllNotesCallCount = 0
        private set

    override suspend fun addNote(note: NoteModel): Result<Unit> {
        addNoteCallCount++
        return doneOrFailure { notes.add(note) }
    }

    override suspend fun deleteNote(id: Long): Result<Unit> {
        deleteNoteCallsCount++
        return doneOrFailure { notes.remove(notes.first { it.id == id }) }
    }


    fun doesExist(id: Long) = notes.any { it.id == id }
    override suspend fun getAllNotes(): Result<List<NoteModel>> {
        getAllNotesCallCount++
        return doneOrFailure { notes.toList() }
    }


    private fun <T> doneOrFailure(action: () -> T): Result<T> {
        return if (returnFailure) {
            Result.failure<T>(exception)
        } else {
            return Result.success(action())
        }
    }

}