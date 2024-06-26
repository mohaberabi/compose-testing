package com.mohaberabi.testingexample.feature.listing.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.testingexample.core.domain.model.NoteModel
import com.mohaberabi.testingexample.core.domain.usecase.DeleteNoteUseCase
import com.mohaberabi.testingexample.core.domain.usecase.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteListingViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    companion object {
        const val ERROR_TITLE = "Something Went Wrong"
    }


    private val _event = Channel<NoteListingEvents>()
    val event = _event.receiveAsFlow()

    private val _notesList = MutableStateFlow(emptyList<NoteModel>())
    val notesList = _notesList.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun loadNotes() {
        viewModelScope.launch {
            getAllNotesUseCase()
                .onSuccess { notes ->
                    _notesList.update { notes }
                    _error.update { null }
                }.onFailure {
                    _notesList.update { emptyList() }
                    _error.update { ERROR_TITLE }
                }
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(id)
                .onSuccess {
                    _event.send(NoteListingEvents.Deleted)
                    loadNotes()
                }
                .onFailure { _event.send(NoteListingEvents.Error) }
        }
    }

}