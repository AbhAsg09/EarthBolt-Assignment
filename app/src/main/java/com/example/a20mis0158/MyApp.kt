package com.example.a20mis0158

import android.app.Application
import com.example.a20mis0158.AppDatabase

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize the database instance
        AppDatabase.getDatabase(this)
    }
}
