package com.mohaberabi.testingexample.core.data.local.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mohaberabi.testingexample.core.data.source.local.database.db.NoteDb
import com.mohaberabi.testingexample.core.data.source.local.database.entity.NoteEntity
import com.mohaberabi.testingexample.core.data.source.local.database.dao.NoteDao
import com.mohaberabi.testingexample.core.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
@SmallTest
@UninstallModules(AppModule::class)
class NoteDaoTest {
    private val fakeNotes = listOf<NoteEntity>(
        NoteEntity(
            title = "ttl1", body = "body1", addedAtMillis = 0L, imageUrl = "img",
        ),
        NoteEntity(
            title = "ttl2", body = "body2", addedAtMillis = 0L, imageUrl = "img",
        ),
        NoteEntity(
            title = "ttl3", body = "body3", addedAtMillis = 0L, imageUrl = "img",
        )
    )

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    lateinit var noteDb: NoteDb

    private lateinit var noteDao: NoteDao

    @Before
    fun setup() {
        hiltRule.inject()
        noteDao = noteDb.noteDao()
    }


    @After
    fun tearDown() {
        noteDb.close()
    }


    @Test
    fun whenGettingNotesFromDbReturnsEmptyList() = runTest {

        val notes = noteDao.getAllNotes()
        assertThat(notes).isEmpty()


    }

    @Test
    fun whenDaoHasItemsShouldReturnThem() = runTest {


        for (item in fakeNotes) {
            noteDao.insertNote(item)
        }
        val items = noteDao.getAllNotes()

        assertThat(fakeNotes).isEqualTo(items)

    }


    @Test
    fun whenNoteDeletesItShouldNotExistInDb() = runTest {
        for (item in fakeNotes) {
            noteDao.insertNote(item)
        }
        noteDao.deleteNote(1L)
        val items = noteDao.getAllNotes().toSet()

        assertThat(items.contains(fakeNotes[0])).isFalse()
    }


}