package com.mohaberabi.testingexample.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mohaberabi.testingexample.core.di.AppModule
import com.mohaberabi.testingexample.core.presentation.MainActivity
import com.mohaberabi.testingexample.feature.listing.presentation.screen.NoteListingScreenTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteListingScreenTest {
    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun addedNoteIsRenderedInList() {
        NoteListingScreenRobot(composeRule)
            .clickOnFab()
            .typeNoteTitle()
            .typeNoteBody()
            .saveNote()
            .assertNoteAdded()
    }

}