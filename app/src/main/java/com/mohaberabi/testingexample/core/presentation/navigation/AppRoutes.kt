package com.mohaberabi.testingexample.core.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohaberabi.testingexample.feature.addnote.presentation.screen.AddNoteScreen
import com.mohaberabi.testingexample.feature.listing.presentation.screen.NoteListScreen
import kotlinx.serialization.Serializable


sealed interface AppRoutes {
    @Serializable
    data object NoteList : AppRoutes

    @Serializable
    data object AddNote : AppRoutes
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppRoutes.NoteList
    ) {

        composable<AppRoutes.NoteList> {
            NoteListScreen(
                onNavigateToAddNote = {
                    navController.navigate(AppRoutes.AddNote)
                }
            )
        }

        composable<AppRoutes.AddNote> {
            AddNoteScreen(
                onSave = {
                    navController.popBackStack()
                }
            )
        }
    }
}
