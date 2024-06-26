package com.mohaberabi.testingexample.core.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mohaberabi.testingexample.core.data.repository.DefaultImageRepository
import com.mohaberabi.testingexample.core.data.source.local.database.dao.NoteDao
import com.mohaberabi.testingexample.core.data.source.local.database.db.NoteDb
import com.mohaberabi.testingexample.core.data.source.local.source.RoomNoteLocalDataSource
import com.mohaberabi.testingexample.core.data.repository.DefaultNoteRepository
import com.mohaberabi.testingexample.core.data.source.remote.ApiConst
import com.mohaberabi.testingexample.core.data.source.remote.api.ImageApi
import com.mohaberabi.testingexample.core.di.fakes.data.source.FakeImagesApi
import com.mohaberabi.testingexample.core.domain.repository.ImageRepository
import com.mohaberabi.testingexample.core.domain.repository.NoteRepository
import com.mohaberabi.testingexample.core.domain.source.local.NoteLocalDataSource
import com.mohaberabi.testingexample.core.domain.usecase.AddNoteUseCase
import com.mohaberabi.testingexample.core.domain.usecase.DeleteNoteUseCase
import com.mohaberabi.testingexample.core.domain.usecase.GetAllNotesUseCase
import com.mohaberabi.testingexample.core.domain.usecase.SearchImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideNoteDb(
        @ApplicationContext context: Context,
    ): NoteDb = Room.databaseBuilder(
        context,
        NoteDb::class.java,
        "note.db"
    ).build()


    @Provides
    @Singleton
    fun provideNoteDao(
        database: NoteDb,
    ): NoteDao = database.noteDao()


    @Provides
    @Singleton
    fun provideNoteLocalDataSource(
        dao: NoteDao,
    ): NoteLocalDataSource = RoomNoteLocalDataSource(dao)


    @Provides
    @Singleton
    fun provideNoteRepository(
        dataSource: NoteLocalDataSource
    ): NoteRepository = DefaultNoteRepository(dataSource)


    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(
        noteRepository: NoteRepository,
    ): GetAllNotesUseCase = GetAllNotesUseCase(noteRepository)

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(
        noteRepository: NoteRepository,
    ): DeleteNoteUseCase = DeleteNoteUseCase(noteRepository)

    @Provides
    @Singleton
    fun provideAddNoteUseCase(
        noteRepository: NoteRepository,
    ): AddNoteUseCase = AddNoteUseCase(noteRepository)


    @Provides
    @Singleton
    fun provideImageApi(): ImageApi = FakeImagesApi()


    @Provides
    @Singleton
    fun provideSearchImageUseCase(
        imageRepository: ImageRepository,
    ): SearchImageUseCase =
        SearchImageUseCase(imageRepository)


    @Provides
    @Singleton
    fun provideImageRepository(
        api: ImageApi,
    ): ImageRepository = DefaultImageRepository(api = api)
}


