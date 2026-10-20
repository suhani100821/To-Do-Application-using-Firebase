package com.example.c117suhanijainassignment2.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TaskRepository {

    // Everything is stored under the "tasks" node of the database
    private val tasksRef = FirebaseDatabase.getInstance().getReference("tasks")

    // READ: a Flow that emits the full task list every time data changes
    fun observeTasks(): Flow<List<Task>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children.mapNotNull { child ->
                    child.getValue(Task::class.java)?.copy(id = child.key ?: "")
                }
                trySend(list)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        tasksRef.addValueEventListener(listener)
        awaitClose { tasksRef.removeEventListener(listener) }
    }

    // CREATE: push() generates a unique key, then we save the task under it
    fun addTask(description: String, priority: Priority) {
        val key = tasksRef.push().key ?: return
        val task = Task(
            id = key,
            description = description,
            priority = priority.value,
            createdAt = System.currentTimeMillis()
        )
        tasksRef.child(key).setValue(task)
    }

    // UPDATE: change only the fields that can be edited
    fun updateTask(id: String, description: String, priority: Priority) {
        val updates = mapOf<String, Any>(
            "description" to description,
            "priority" to priority.value
        )
        tasksRef.child(id).updateChildren(updates)
    }

    // DELETE: remove the whole node
    fun deleteTask(id: String) {
        tasksRef.child(id).removeValue()
    }
}
