package com.ub.todoapplication_jetpackcompose.data.repositories

import com.ub.todoapplication_jetpackcompose.data.db.dao.TodoDao
import com.ub.todoapplication_jetpackcompose.data.models.Priorities
import com.ub.todoapplication_jetpackcompose.data.models.TodoModel
import com.ub.todoapplication_jetpackcompose.utils.Utils
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TodoRepository @Inject constructor (private val todoDao: TodoDao) {
    val getAllTodos: Flow<List<TodoModel>> = todoDao.getAllTodos()

    suspend fun addTodo(todoModel: TodoModel){
        todoDao.addTodoTask(todoModel)
    }


    suspend fun updateTodo(
        id: Int,
        title: String,
        description: String,
        priority: Priorities,
        isChecked: Boolean
    ){
        Utils.appLogCalls(message = "updatedCall -- $id, $title, $description, $isChecked")
        todoDao.updateTodo(id, title, description, priority, isChecked)
    }

    suspend fun deleteTodo(todoModel: List<TodoModel>){
        todoDao.deleteTodo(todoModel)
    }




}