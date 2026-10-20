package com.example.c117suhanijainassignment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c117suhanijainassignment2.data.Priority
import com.example.c117suhanijainassignment2.data.Task
import com.example.c117suhanijainassignment2.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TaskViewModel(
    private val repository: TaskRepository = TaskRepository()
) : ViewModel() {

    // Sorted: High -> Medium -> Low, and oldest first inside the same priority
    val tasks: StateFlow<List<Task>> = repository.observeTasks()
        .map { list ->
            list.sortedWith(compareBy<Task>({ it.priority }, { it.createdAt }))
        }
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addTask(description: String, priority: Priority) =
        repository.addTask(description, priority)

    fun updateTask(id: String, description: String, priority: Priority) =
        repository.updateTask(id, description, priority)

    fun deleteTask(id: String) =
        repository.deleteTask(id)
}
