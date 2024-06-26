package com.mohaberabi.testingexample.core.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.testingexample.core.data.source.local.database.entity.NoteEntity


@Dao
interface NoteDao {


    @Upsert

    suspend fun insertNote(note: NoteEntity)


    @Query("DELETE FROM note WHERE id=:id")
    suspend fun deleteNote(id: Long)

    @Query("SELECT  * FROM note")

    suspend fun getAllNotes(): List<NoteEntity>
}