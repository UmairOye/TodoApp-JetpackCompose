package com.ub.todoapplication_jetpackcompose.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ub.todoapplication_jetpackcompose.data.models.Priorities
import com.ub.todoapplication_jetpackcompose.data.models.TodoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert
    suspend fun addTodoTask(todoModel: TodoModel)

    @Query("SELECT * FROM tbl_todo ORDER BY id ASC")
    fun getAllTodos(): Flow<List<TodoModel>>


    @Query("UPDATE tbl_todo SET title = :title, description = :description, priority = :priority, isChecked = :isChecked WHERE id = :id")
    suspend fun updateTodo(
        id: Int,
        title: String,
        description: String,
        priority: Priorities,
        isChecked: Boolean
    )


    @Delete
    suspend fun deleteTodo(todoModel: List<TodoModel>)

}