package com.example.c117suhanijainassignment2.data

enum class Priority(val value: Int, val label: String) {
    HIGH(0, "High"),
    MEDIUM(1, "Medium"),
    LOW(2, "Low");

    companion object {
        fun fromValue(value: Int): Priority =
            entries.firstOrNull { it.value == value } ?: MEDIUM
    }
}
