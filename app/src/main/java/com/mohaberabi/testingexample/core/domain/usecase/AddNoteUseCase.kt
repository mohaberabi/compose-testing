package com.mohaberabi.testingexample.core.domain.usecase

import com.mohaberabi.testingexample.core.domain.model.NoteModel
import com.mohaberabi.testingexample.core.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) {

    suspend operator fun invoke(note: NoteModel) = noteRepository.addNote(note = note)

}