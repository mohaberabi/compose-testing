package com.mohaberabi.testingexample.core.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mohaberabi.testingexample.fakes.data.repos.FakeNoteRepository
import com.mohaberabi.testingexample.core.domain.model.NoteModel
import com.mohaberabi.testingexample.core.domain.usecase.GetAllNotesUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetAllNotesUseCaseTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var getAllNotesUseCase: GetAllNotesUseCase


    private lateinit var noteRepository: FakeNoteRepository

    @Before
    fun setup() {
        noteRepository = FakeNoteRepository()
        getAllNotesUseCase = GetAllNotesUseCase(noteRepository)
    }


    @Test
    fun ` returns done carrying the list of items , calls once`() = runTest {


        val result = getAllNotesUseCase()
        assertThat(result).isEqualTo(Result.success(noteRepository.notesList))
        assertThat(noteRepository.getAllNotesCallCount).isEqualTo(1)
    }


    @Test
    fun ` returns failure , calls never`() = runTest {
        noteRepository.returnFailure = true
        val result = getAllNotesUseCase()
        assertThat(result).isEqualTo(Result.failure<List<NoteModel>>(noteRepository.exception))
        assertThat(noteRepository.getAllNotesCallCount).isEqualTo(1)
    }
}