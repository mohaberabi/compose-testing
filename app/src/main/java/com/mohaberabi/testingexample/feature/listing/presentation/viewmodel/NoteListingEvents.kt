package com.mohaberabi.testingexample.feature.listing.presentation.viewmodel

sealed interface NoteListingEvents {


    data object Error : NoteListingEvents
    data object Deleted : NoteListingEvents

}