package com.mohaberabi.testingexample.core.domain.usecase

import com.mohaberabi.testingexample.core.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) {

    suspend operator fun invoke(id: Long): Result<Unit> = noteRepository.deleteNote(id = id)
}