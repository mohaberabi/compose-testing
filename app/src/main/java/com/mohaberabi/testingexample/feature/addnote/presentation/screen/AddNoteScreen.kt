package com.mohaberabi.testingexample.feature.addnote.presentation.screen


import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.mohaberabi.testingexample.feature.addnote.presentation.viemwodel.AddNoteEvents
import com.mohaberabi.testingexample.feature.addnote.presentation.viemwodel.AddNoteViewModel
import com.mohaberabi.testingexample.feature.listing.presentation.screen.EventCollector
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNoteScreen(
    onSave: () -> Unit,
    addNoteViewModel: AddNoteViewModel = hiltViewModel(),

    ) {


    val snackBarHostState = SnackbarHostState()

    val scope = rememberCoroutineScope()
    EventCollector(flow = addNoteViewModel.event) { event ->
        when (event) {
            AddNoteEvents.Error -> {
                scope.launch {
                    snackBarHostState.showSnackbar("An Error happened please try agian")
                }
            }

            AddNoteEvents.NonValid -> {
                scope.launch {
                    snackBarHostState.showSnackbar("Please input required inputs")
                }
            }

            AddNoteEvents.NoteAdded -> onSave()
        }
    }
    val title by addNoteViewModel.title.collectAsStateWithLifecycle()

    val body by addNoteViewModel.body.collectAsStateWithLifecycle()
    val image by addNoteViewModel.image.collectAsStateWithLifecycle()
    val imageSearchQuery by addNoteViewModel.imgSearchQuery.collectAsStateWithLifecycle()
    val images by addNoteViewModel.images.collectAsStateWithLifecycle()

    val loadingImages by addNoteViewModel.loading.collectAsStateWithLifecycle()

    val showDialog by addNoteViewModel.showImageDialog.collectAsStateWithLifecycle()



    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) {

            padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable {
                        addNoteViewModel.toggleDialog()
                    }
                    .testTag(
                        "noteImage"
                    ),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(image)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = "noteImage",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("ttlField"),
                value = title,
                onValueChange = {
                    addNoteViewModel.titleChanged(it)
                },
                label = {
                    Text(text = "title")
                },
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("bodyField"),
                value = body,
                onValueChange = {
                    addNoteViewModel.bodyChanged(it)
                },
                label = {
                    Text(text = "Body")
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("saveButton"),
                onClick = {
                    addNoteViewModel.addNote()
                }
            ) {
                Text(
                    text = "Save",
                    fontSize = 17.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }


    if (showDialog) {
        Dialog(
            onDismissRequest = {
                addNoteViewModel.toggleDialog()
            }
        ) {
            ImagesDialogContent(
                query = imageSearchQuery,
                loading = loadingImages,
                images = images,
                onSearchQueryChange = {
                    addNoteViewModel.imageSearchQueryChanged(it)
                },
                onImageClick = {
                    addNoteViewModel.imageChanged(it)
                },
            )
        }
    }
}

@Composable
fun ImagesDialogContent(
    query: String,
    onSearchQueryChange: (String) -> Unit,
    onImageClick: (String) -> Unit,
    images: List<String>,
    loading: Boolean,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .clip(RoundedCornerShape(26.dp))
            .background(MaterialTheme.colorScheme.background)
            .testTag("searchImgDialog"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("searchImgField"),
            value = query,
            onValueChange = {
                onSearchQueryChange(it)
            },
            label = {
                Text(text = "Search image")
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(120.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {

                itemsIndexed(images) { index, url ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable { onImageClick(url) }
                            .testTag("networkImg$url"),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(url)
                            .size(Size.ORIGINAL)
                            .build(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
    }
}




















