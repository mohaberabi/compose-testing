package com.mohaberabi.testingexample.core.domain.usecase

import com.mohaberabi.testingexample.core.domain.model.NoteModel
import com.mohaberabi.testingexample.core.domain.repository.NoteRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) {

    suspend operator fun invoke(): Result<List<NoteModel>> = noteRepository.getAllNotes()
}