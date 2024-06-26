package com.mohaberabi.testingexample.feature.listing

import com.google.common.truth.Truth.assertThat
import com.mohaberabi.testingexample.core.domain.usecase.DeleteNoteUseCase
import com.mohaberabi.testingexample.core.domain.usecase.GetAllNotesUseCase
import com.mohaberabi.testingexample.fakes.data.repos.FakeNoteRepository
import com.mohaberabi.testingexample.feature.listing.presentation.viewmodel.NoteListingEvents
import com.mohaberabi.testingexample.feature.listing.presentation.viewmodel.NoteListingViewModel
import com.mohaberabi.testingexample.rules.MainCoroutineRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteListViewModelTest {


    @get:Rule
    val testDispatcher = MainCoroutineRule()


    private lateinit var getAllNotesUseCase: GetAllNotesUseCase
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var noteListingViewModel: NoteListingViewModel
    private lateinit var noteRepository: FakeNoteRepository

    @Before
    fun setup() {
        noteRepository = FakeNoteRepository()
        getAllNotesUseCase = GetAllNotesUseCase(noteRepository)
        deleteNoteUseCase = DeleteNoteUseCase(noteRepository)
        noteListingViewModel =
            NoteListingViewModel(
                getAllNotesUseCase = getAllNotesUseCase,
                deleteNoteUseCase = deleteNoteUseCase,
            )
    }


    @Test
    fun `when get all notes should update the state with the notes if the repo is done called once  `() =
        runTest {
            noteListingViewModel.loadNotes()
            testDispatcher.dispatcher.scheduler.advanceUntilIdle()
            val value = noteListingViewModel.notesList.value
            assertThat(value).isEqualTo(noteRepository.notesList)
            assertThat(noteRepository.getAllNotesCallCount).isEqualTo(1)
            assertThat(noteListingViewModel.error.value).isEqualTo(null)
        }


    @Test
    fun `when get all notes should not  update the state with the notes if the repos is error called never `() =
        runTest {
            noteRepository.returnFailure = true
            noteListingViewModel.loadNotes()
            testDispatcher.dispatcher.scheduler.advanceUntilIdle()
            val value = noteListingViewModel.notesList.value
            assertThat(value.isEmpty()).isTrue()
            assertThat(noteRepository.getAllNotesCallCount).isEqualTo(1)
            assertThat(noteListingViewModel.error.value).isEqualTo(NoteListingViewModel.ERROR_TITLE)
        }


    @Test
    fun ` when delete a note should removed from db and send deleted event called once`() =
        runTest {
            noteListingViewModel.deleteNote(0L)
            testDispatcher.dispatcher.scheduler.advanceUntilIdle()
            val value = noteListingViewModel.notesList.value
            val event = noteListingViewModel.event.first()
            assertThat(event).isEqualTo(NoteListingEvents.Deleted)
            assertThat(value.any { it.id == 0L }).isFalse()
            assertThat(noteRepository.doesExist(0L)).isFalse()
            assertThat(noteRepository.deleteNoteCallsCount).isEqualTo(1)

        }

    @Test
    fun ` when delete a note should not removed from db and send error event called never`() =
        runTest {
            noteRepository.returnFailure = true
            noteListingViewModel.deleteNote(0L)
            testDispatcher.dispatcher.scheduler.advanceUntilIdle()
            val value = noteListingViewModel.notesList.value
            val event = noteListingViewModel.event.first()
            assertThat(event).isEqualTo(NoteListingEvents.Error)
            assertThat(value.isEmpty()).isTrue()
            assertThat(noteRepository.doesExist(0L)).isTrue()
            assertThat(noteRepository.deleteNoteCallsCount).isEqualTo(1)
        }
}