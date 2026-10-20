package com.example.c117suhanijainassignment2.data

data class Task(
    val id: String = "",
    val description: String = "",
    val priority: Int = Priority.MEDIUM.value,
    val createdAt: Long = 0L
)
