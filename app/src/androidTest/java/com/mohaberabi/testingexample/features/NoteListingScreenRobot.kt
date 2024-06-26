package com.mohaberabi.testingexample.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mohaberabi.testingexample.core.presentation.MainActivity
import com.mohaberabi.testingexample.feature.listing.presentation.screen.NoteListingScreenTags


typealias ActivityComposeRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

class NoteListingScreenRobot(
    private val composeRule: ActivityComposeRule
) {


    fun clickOnFab(): NoteListingScreenRobot {
        composeRule
            .onNodeWithTag(NoteListingScreenTags.ADD_NOTE_FAB)
            .performClick()
        return this
    }
    
    fun typeNoteTitle(title: String = "title"): NoteListingScreenRobot {
        composeRule.onNodeWithTag("ttlField")
            .performTextInput(title)
        return this
    }

    fun typeNoteBody(body: String = "body"): NoteListingScreenRobot {
        composeRule.onNodeWithTag("bodyField")
            .performTextInput(body)
        return this
    }

    fun saveNote(): NoteListingScreenRobot {
        composeRule
            .onNodeWithText("Save")
            .performClick()
        return this
    }

    fun assertNoteAdded(title: String = "title"): NoteListingScreenRobot {
        composeRule.onNodeWithText(title)
            .assertIsDisplayed()
        return this
    }
}