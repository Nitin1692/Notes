package com.example.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService

import java.util.concurrent.Executors

@Entity
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object{
        @Volatile
        private var INSTANCE: NoteDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context): NoteDatabase? {
            if (INSTANCE == null) {
                synchronized(NoteDatabase::class.java) {

                        INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NoteDatabase::class.java, "note_database"
                        )
                            .build()

                }
            }
            return INSTANCE
        }
    }
}