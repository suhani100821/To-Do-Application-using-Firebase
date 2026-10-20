package com.example.c117suhanijainassignment2

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
