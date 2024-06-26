package com.mohaberabi.testingexample.feature.addnote

import com.google.common.truth.Truth.assertThat
import com.mohaberabi.testingexample.core.domain.usecase.AddNoteUseCase
import com.mohaberabi.testingexample.core.domain.usecase.SearchImageUseCase
import com.mohaberabi.testingexample.fakes.data.repos.FakeImageRepository
import com.mohaberabi.testingexample.fakes.data.repos.FakeNoteRepository
import com.mohaberabi.testingexample.feature.addnote.presentation.viemwodel.AddNoteEvents
import com.mohaberabi.testingexample.feature.addnote.presentation.viemwodel.AddNoteViewModel
import com.mohaberabi.testingexample.rules.MainCoroutineRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AddNoteViewModelTest {


    private lateinit var imageRepository: FakeImageRepository

    private lateinit var noteRepository: FakeNoteRepository
    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var addNoteViewModel: AddNoteViewModel
    private lateinit var searchImagesUseCase: SearchImageUseCase


    @get:Rule
    val dispatcher = MainCoroutineRule()


    @Before

    fun setup() {
        noteRepository = FakeNoteRepository()
        addNoteUseCase = AddNoteUseCase(noteRepository)
        searchImagesUseCase = SearchImageUseCase(imageRepository)

        addNoteViewModel = AddNoteViewModel(addNoteUseCase, searchImagesUseCase)
    }


    @Test
    fun `should update title`() {
        addNoteViewModel.titleChanged("mohab")
        assertThat(addNoteViewModel.title.value).isEqualTo("mohab")
    }

    @Test
    fun `should update body`() {
        addNoteViewModel.bodyChanged("mohab")
        assertThat(addNoteViewModel.body.value).isEqualTo("mohab")
    }

    @Test
    fun `should update image`() {
        addNoteViewModel.imageChanged("mohab")
        assertThat(addNoteViewModel.image.value).isEqualTo("mohab")
    }


    @Test
    fun `emits false  when title , body is empty`() =
        assertThat(addNoteViewModel.canAdd.value).isEqualTo(false)


    @Test
    fun `emits false  when body is empty`() {
        addNoteViewModel.titleChanged("mmm")
        assertThat(addNoteViewModel.canAdd.value).isEqualTo(false)
    }

    @Test
    fun `emits false  when title is empty`() {
        addNoteViewModel.bodyChanged("mmm")
        assertThat(addNoteViewModel.canAdd.value).isEqualTo(false)
    }

    @Test
    fun `emits true   when title && body is notEmpty`() = runTest {
        addNoteViewModel.bodyChanged("mmm")
        addNoteViewModel.titleChanged("mmm")
        dispatcher.dispatcher.scheduler.advanceUntilIdle()
        val can = addNoteViewModel.canAdd.value
        assertThat(can).isTrue()
    }

    @Test

    fun `when adding non valid note sends non valid event`() = runTest {
        addNoteViewModel.addNote()
        dispatcher.dispatcher.scheduler.advanceUntilIdle()
        val event = addNoteViewModel.event
        assertThat(event.first()).isEqualTo(AddNoteEvents.NonValid)
        assertThat(addNoteViewModel.loading.value).isFalse()
        assertThat(addNoteViewModel.canAdd.value).isFalse()
        assertThat(addNoteViewModel.body.value).isEmpty()
        assertThat(addNoteViewModel.title.value).isEmpty()
        assertThat(noteRepository.addNoteCallCount).isEqualTo(1)

    }


    @Test
    fun `when adding a valid note added to db called once , sends added event `() = runTest {
        addNoteViewModel.bodyChanged("mmm")
        addNoteViewModel.titleChanged("mmm")
        dispatcher.dispatcher.scheduler.advanceUntilIdle()
        addNoteViewModel.addNote()
        val event = addNoteViewModel.event
        assertThat(addNoteViewModel.canAdd.value).isTrue()
        assertThat(event.first()).isEqualTo(AddNoteEvents.NoteAdded)
        assertThat(addNoteViewModel.body.value).isEqualTo("mmm")
        assertThat(addNoteViewModel.title.value).isEqualTo("mmm")
        assertThat(noteRepository.addNoteCallCount).isEqualTo(1)
    }


    @Test
    fun `when adding a valid note added to db called never , sends error event `() = runTest {
        noteRepository.returnFailure = true
        addNoteViewModel.bodyChanged("mmm")
        addNoteViewModel.titleChanged("mmm")
        dispatcher.dispatcher.scheduler.advanceUntilIdle()
        addNoteViewModel.addNote()
        val event = addNoteViewModel.event
        assertThat(addNoteViewModel.canAdd.value).isTrue()
        assertThat(event.first()).isEqualTo(AddNoteEvents.Error)
        assertThat(addNoteViewModel.body.value).isEqualTo("mmm")
        assertThat(addNoteViewModel.title.value).isEqualTo("mmm")
        assertThat(noteRepository.addNoteCallCount).isEqualTo(1)
    }

}