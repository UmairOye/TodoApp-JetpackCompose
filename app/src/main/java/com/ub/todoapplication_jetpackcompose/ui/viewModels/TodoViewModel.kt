package com.ub.todoapplication_jetpackcompose.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ub.todoapplication_jetpackcompose.data.models.Priorities
import com.ub.todoapplication_jetpackcompose.data.models.TodoModel
import com.ub.todoapplication_jetpackcompose.data.repositories.TodoRepository
import com.ub.todoapplication_jetpackcompose.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: TodoRepository): ViewModel() {
    private val _selectedTodoIds = mutableStateOf(setOf<Int>()) // Keep track of selected items
    val selectedTodoIds: State<Set<Int>> = _selectedTodoIds
    var selectedPriority = Priorities.LOW
    private val _allTodos = MutableStateFlow<RequestState<List<TodoModel>>>(RequestState.Idle)
    val allTodos: StateFlow<RequestState<List<TodoModel>>> = _allTodos.asStateFlow()
    var updatedTodo: TodoModel? = null
    var selectedTodoList = mutableListOf<TodoModel>()
    var isForUpdatePurpose: Boolean = false

    fun addTodo(todoModel: TodoModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTodo(todoModel)
        }
    }


    fun toggleSelection(todoId: Int) {
        _selectedTodoIds.value = if (_selectedTodoIds.value.contains(todoId)) {
            _selectedTodoIds.value - todoId
        } else {
            _selectedTodoIds.value + todoId
        }
    }

    fun resetSelection() {
        viewModelScope.launch {
            val currentState = _allTodos.value
            if (currentState is RequestState.Success) {
                _allTodos.value = RequestState.Success(
                    currentState.data.map { it.copy(isChecked = false) }
                )
            }
        }

    }

    fun updateTodo(
        id: Int,
        title: String,
        description: String,
        priority: Priorities,
        isChecked: Boolean
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(id, title, description, priority, isChecked)
        }
    }


    fun deleteTodo(todoModel: List<TodoModel>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todoModel)
        }
    }

    init {
        getTodoList()
    }



    private fun getTodoList(){
        _allTodos.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTodos.collect{ allTodosList ->
                    _allTodos.value = RequestState.Success(allTodosList)
                }
            }
        }catch (ex: Exception){
            _allTodos.value = RequestState.Error(ex)
            ex.printStackTrace()
        }
    }
}