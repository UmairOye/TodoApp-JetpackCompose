package com.ub.todoapplication_jetpackcompose.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ub.todoapplication_jetpackcompose.R
import com.ub.todoapplication_jetpackcompose.data.models.TodoModel
import com.ub.todoapplication_jetpackcompose.navigations.NavigationItem
import com.ub.todoapplication_jetpackcompose.ui.components.NoItemsAvailable
import com.ub.todoapplication_jetpackcompose.ui.components.TextWithDrawableLeft
import com.ub.todoapplication_jetpackcompose.ui.components.TodoListItems
import com.ub.todoapplication_jetpackcompose.ui.theme.MEDIUM_PADDING
import com.ub.todoapplication_jetpackcompose.ui.theme.SMALL_PADDING
import com.ub.todoapplication_jetpackcompose.ui.viewModels.TodoViewModel
import com.ub.todoapplication_jetpackcompose.utils.RequestState
import com.ub.todoapplication_jetpackcompose.utils.Utils

@Composable
fun ShowTodoList(onNavigate:(route: String)-> Unit,
                 onPressingBack:()->Unit,
                 viewModel: TodoViewModel) {

    //used remember update state for avoid any necessary changed caused by configurations
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    val allTodo by viewModel.allTodos.collectAsState()
    var alpha by remember {
        mutableFloatStateOf(0f)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            //remove selection from item when user make selection and go back to previous screen
            viewModel.resetSelection()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TextWithDrawableLeft(
                text = stringResource(id = R.string.all_todos)
            ) {
                onPressingBack()
            }


            if(allTodo is RequestState.Success) {
                val todoList = allTodo as RequestState.Success<List<TodoModel>>
                if(todoList.data.isNotEmpty()){
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    LazyVerticalStaggeredGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(8f),
                        columns = StaggeredGridCells.Adaptive(150.dp),
                        verticalItemSpacing = 2.dp,
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        content = {
                            items((allTodo as RequestState.Success<List<TodoModel>>).data, key = {it.id}) { todoListItem ->
                                TodoListItems(todoListItem, maxLines = 50, isCheckedVisible = true, onItemChecked = {
                                    val count = todoList.data.count { it.isChecked }
                                    viewModel.selectedTodoList = todoList.data.filter { it.isChecked }.toMutableList()
                                    alpha = if(count>0){
                                        1f
                                    }else{
                                        0f
                                    }
                                },
                                    onItemClick = {
                                        viewModel.updatedTodo = todoListItem
                                        viewModel.isForUpdatePurpose = true
                                        onNavigate(NavigationItem.ADD_TODO.route)
                                    })
                            }
                        }
                    )
                }else{
                    NoItemsAvailable()
                }
            }


            Button(onClick = {
                viewModel.deleteTodo(viewModel.selectedTodoList)
                viewModel.resetSelection()
                alpha = 0f
            },modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(alpha = alpha)
                .padding(all = MEDIUM_PADDING)
                .height(50.dp)
                .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                shape = RoundedCornerShape(SMALL_PADDING)
            ) {
                Text(
                    text = stringResource(R.string.delete_items),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}
