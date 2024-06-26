package com.mohaberabi.testingexample.core.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.mohaberabi.testingexample.fakes.data.repos.FakeNoteRepository
import com.mohaberabi.testingexample.core.domain.usecase.DeleteNoteUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteNoteUseCaseTest {


    private lateinit var deleteNoteUseCaseTest: DeleteNoteUseCase

    private lateinit var noteRepository: FakeNoteRepository


    @Before
    fun setup() {
        noteRepository = FakeNoteRepository()
        deleteNoteUseCaseTest = DeleteNoteUseCase(noteRepository)
    }


    @Test
    fun ` when delete note returns done called once`() = runTest {


        val result = deleteNoteUseCaseTest(0L)

        assertThat(result).isEqualTo(Result.success(Unit))
        assertThat(noteRepository.deleteNoteCallsCount).isEqualTo(1)

    }

    @Test
    fun ` when delete note returns failure  called never`() = runTest {
        noteRepository.returnFailure = true
        val result = deleteNoteUseCaseTest(0L)
        assertThat(result).isEqualTo(Result.failure<Unit>(noteRepository.exception))
        assertThat(noteRepository.deleteNoteCallsCount).isEqualTo(1)

    }
}