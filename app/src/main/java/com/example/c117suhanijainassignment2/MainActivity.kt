package com.example.c117suhanijainassignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.c117suhanijainassignment2.ui.TodoScreen
import com.example.c117suhanijainassignment2.ui.theme.C117SuhaniJainAssignment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            C117SuhaniJainAssignment2Theme {
                TodoScreen()
            }
        }
    }
}
