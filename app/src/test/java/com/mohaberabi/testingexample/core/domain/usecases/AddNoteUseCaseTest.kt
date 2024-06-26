package com.mohaberabi.testingexample.core.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.mohaberabi.testingexample.fakes.data.repos.FakeNoteRepository
import com.mohaberabi.testingexample.core.domain.model.NoteModel
import com.mohaberabi.testingexample.core.domain.usecase.AddNoteUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddNoteUseCaseTest {


    private val note = NoteModel(
        id = 100L,
        title = "ttttsdasd",
        body = "asdlsadnlsia",
        addedAtMillis = 0L,
        imageUrl = "kjbskjbaskj"
    )
    private lateinit var addNoteUseCase: AddNoteUseCase

    private lateinit var noteRepository: FakeNoteRepository

    @Before
    fun setup() {
        noteRepository = FakeNoteRepository()
        addNoteUseCase = AddNoteUseCase(noteRepository)
    }


    @Test
    fun `when add note ,return done and calls once`() = runTest {
        val result = addNoteUseCase(note)
        assertThat(result).isEqualTo(Result.success(Unit))
        assertThat(noteRepository.addNoteCallCount).isEqualTo(1)
    }

    @Test
    fun `when add note ,return failure and calls never`() = runTest {
        noteRepository.returnFailure = true
        val result = addNoteUseCase(note)
        assertThat(result).isEqualTo(Result.failure<Unit>(noteRepository.exception))
        assertThat(noteRepository.addNoteCallCount).isEqualTo(1)
    }
}