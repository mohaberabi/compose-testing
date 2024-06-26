package com.mohaberabi.testingexample.feature.addnote.presentation.viemwodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.testingexample.core.domain.model.NoteModel
import com.mohaberabi.testingexample.core.domain.usecase.AddNoteUseCase
import com.mohaberabi.testingexample.core.domain.usecase.SearchImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject


@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val searchImagesUseCase: SearchImageUseCase,
) : ViewModel() {


    private val _images = MutableStateFlow(listOf<String>())
    val images = _images.asStateFlow()
    private val _showImageDialog = MutableStateFlow(false)
    val showImageDialog = _showImageDialog.asStateFlow()
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()
    private val _imgSearchQuery = MutableStateFlow("")
    val imgSearchQuery = _imgSearchQuery.asStateFlow()
    private val _body = MutableStateFlow("")
    val body = _body.asStateFlow()
    private val _image = MutableStateFlow("")
    val image = _image.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    val canAdd: StateFlow<Boolean> =
        combine(_title, _body) { ttl, bdy ->
            (ttl.trim().isNotEmpty() && bdy.trim().isNotEmpty())
        }.map { it }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            false
        )


    private val _event = Channel<AddNoteEvents>()
    val event = _event.receiveAsFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNote() {
        if (!canAdd.value) {

            viewModelScope.launch { _event.send(AddNoteEvents.NonValid) }

        } else {
            _loading.update { true }
            viewModelScope.launch {
                val note = NoteModel(
                    title = _title.value,
                    body = _body.value,
                    imageUrl = _image.value,
                    id = -1L,
                    addedAtMillis = Instant.now().toEpochMilli(),
                )
                addNoteUseCase(note)
                    .onSuccess {
                        _loading.update { false }
                        _event.send(AddNoteEvents.NoteAdded)
                    }
                    .onFailure {
                        _loading.update { false }
                        _event.send(AddNoteEvents.Error)
                    }
            }
        }

    }

    fun toggleDialog() = _showImageDialog.update { !it }
    private fun getImages(q: String) {
        viewModelScope.launch {
            searchImagesUseCase(q)
                .onSuccess { imgs ->
                    _images.update { imgs.images.map { it.url } }
                }
        }
    }

    fun titleChanged(title: String) = _title.update { title }
    fun bodyChanged(body: String) = _body.update { body }
    fun imageChanged(img: String) = _image.update { img }
    fun imageSearchQueryChanged(img: String) {
        _imgSearchQuery.update { img }
        getImages(img)
    }

}