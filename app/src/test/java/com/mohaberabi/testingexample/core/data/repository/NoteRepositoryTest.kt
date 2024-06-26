package com.mohaberabi.testingexample.core.data.repository

import com.google.common.truth.Truth.assertThat
import com.mohaberabi.testingexample.fakes.data.source.FakeNoteLocalDataSource
import com.mohaberabi.testingexample.core.domain.model.NoteModel
import com.mohaberabi.testingexample.core.domain.repository.NoteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NoteRepositoryTest {


    private val note = NoteModel(
        title = "ttl",
        body = "body",
        imageUrl = "img",
        id = 10L,
        addedAtMillis = 0L
    )

    private lateinit var noteRepository: NoteRepository

    private lateinit var noteLocalDataSource: FakeNoteLocalDataSource


    @Before
    fun setup() {
        noteLocalDataSource = FakeNoteLocalDataSource()
        noteRepository = DefaultNoteRepository(noteLocalDataSource)
    }


    @Test
    fun `when add note returns done , adds to source , calls once `() = runTest {

        val result = noteRepository.addNote(
            note
        )
        assertThat(result).isEqualTo(Result.success(Unit))
        assertThat(noteLocalDataSource.addNoteCallCount).isEqualTo(1)
        assertThat(noteLocalDataSource.doesExist(note.id!!)).isTrue()
    }

    @Test
    fun `when add note returns failure , never adds to source , calls never  `() = runTest {
        noteLocalDataSource.throwError = true
        val result = noteRepository.addNote(
            note
        )
        assertThat(result).isEqualTo(Result.failure<Unit>(noteLocalDataSource.exception))
        assertThat(noteLocalDataSource.addNoteCallCount).isEqualTo(0)
    }

    @Test
    fun `when delete note returns done , removes from db ,  `() = runTest {
        val result = noteRepository.deleteNote(0L)
        assertThat(result).isEqualTo(Result.success(Unit))
        assertThat(noteLocalDataSource.deleteNoteCallsCount).isEqualTo(1)
        assertThat(noteLocalDataSource.doesExist(0L)).isFalse()
    }

    @Test
    fun `when delete note returns failure , never removes from db ,  `() = runTest {
        noteLocalDataSource.throwError = true
        val result = noteRepository.deleteNote(0L)
        assertThat(result).isEqualTo(Result.failure<Unit>(noteLocalDataSource.exception))
        assertThat(noteLocalDataSource.deleteNoteCallsCount).isEqualTo(0)
        assertThat(noteLocalDataSource.doesExist(0L)).isTrue()
    }

    @Test
    fun `when get notes returns the done with data `() = runTest {
        val result = noteRepository.getAllNotes()
        assertThat(result).isEqualTo(Result.success(noteLocalDataSource.notesList))
        assertThat(noteLocalDataSource.getAllNotesCallCount).isEqualTo(1)

    }

    @Test
    fun `when get notes returns  failure `() = runTest {
        noteLocalDataSource.throwError = true
        val result = noteRepository.getAllNotes()
        assertThat(result).isEqualTo(Result.failure<List<NoteModel>>(noteLocalDataSource.exception))
        assertThat(noteLocalDataSource.getAllNotesCallCount).isEqualTo(0)

    }
}