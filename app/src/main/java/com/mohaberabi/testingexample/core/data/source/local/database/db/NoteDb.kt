package com.mohaberabi.testingexample.core.data.source.local.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohaberabi.testingexample.core.data.source.local.database.dao.NoteDao
import com.mohaberabi.testingexample.core.data.source.local.database.entity.NoteEntity


@Database(
    entities = [
        NoteEntity::class,
    ],
    version = 1,

    )
abstract class NoteDb : RoomDatabase() {


    abstract fun noteDao(): NoteDao
}