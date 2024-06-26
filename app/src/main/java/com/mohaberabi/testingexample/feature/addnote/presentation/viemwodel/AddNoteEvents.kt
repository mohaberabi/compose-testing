package com.mohaberabi.testingexample.feature.addnote.presentation.viemwodel


sealed interface AddNoteEvents {
    data object Error : AddNoteEvents
    data object NoteAdded : AddNoteEvents
    data object NonValid : AddNoteEvents

}