package com.example.a20mis0158

import android.content.Context
import androidx.room.Room

object AppDatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "photo_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
