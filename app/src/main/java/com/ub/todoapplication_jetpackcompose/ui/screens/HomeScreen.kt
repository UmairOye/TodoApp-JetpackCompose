package com.ub.todoapplication_jetpackcompose.ui.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ub.todoapplication_jetpackcompose.R
import com.ub.todoapplication_jetpackcompose.data.models.TodoModel
import com.ub.todoapplication_jetpackcompose.navigations.NavigationItem
import com.ub.todoapplication_jetpackcompose.ui.components.NoItemsAvailable
import com.ub.todoapplication_jetpackcompose.ui.components.SwipeBox
import com.ub.todoapplication_jetpackcompose.ui.components.TodoListItems
import com.ub.todoapplication_jetpackcompose.ui.viewModels.TodoViewModel
import com.ub.todoapplication_jetpackcompose.utils.RequestState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
//@Preview(showSystemUi = true, name = "home_screen")
fun ShowHomeScreen(onNavigate:(route: String)-> Unit,
                   onPressingBack:()->Unit,
                   viewModel: TodoViewModel) {


    //used remember update state for avoid any necessary changed caused by configurations
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    val allTodos by viewModel.allTodos.collectAsState()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
//                    Log.d("lifeCycleTag", "ShowHomeScreen: ON_START")
                }
                Lifecycle.Event.ON_STOP -> {
//                    Log.d("lifeCycleTag", "ShowHomeScreen: ON_STOP")

                }
                Lifecycle.Event.ON_CREATE -> {
//                    Log.d("lifeCycleTag", "ShowHomeScreen: ON_CREATE")

                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }


        Scaffold {innerPadding ->
        Column(modifier = Modifier.consumeWindowInsets(innerPadding),
            verticalArrangement = Arrangement.Center) {
            Log.d("lifeCycleTag", "ShowHomeScreen: inner composable")


            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)) {

                MakeTopCards(modifier = Modifier.weight(1f), text = stringResource(id = R.string.add_todo), image = R.drawable.add_todo){
                    onNavigate(NavigationItem.ADD_TODO.route)
                }

                MakeTopCards(modifier = Modifier.weight(1f), text = stringResource(R.string.view_todo), image = R.drawable.view_list){
                    onNavigate(NavigationItem.TODO_LIST.route)
                }
            }


            Spacer(modifier = Modifier.padding(top = 20.dp))
            Text(text = stringResource(R.string.all_todo),
                fontSize = 20.sp,
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp)
            )


            Spacer(modifier = Modifier.padding(top = 10.dp))
            if(allTodos is RequestState.Success){
                val todoList = allTodos as RequestState.Success<List<TodoModel>>
                if(todoList.data.isNotEmpty()){
                    LazyColumn {
                        items(todoList.data, key = {it.id}){ todoItem->
                            SwipeBox(onDelete = { viewModel.deleteTodo(listOf(todoItem))}, onEdit = {
                                viewModel.updatedTodo = todoItem
                                onNavigate(NavigationItem.ADD_TODO.route)
                                viewModel.isForUpdatePurpose = true
                            }, modifier = Modifier.animateItemPlacement()
                            ) {
                                TodoListItems(todoItem, maxLines = 2, onItemClick = {
                                    viewModel.updatedTodo = todoItem
                                    viewModel.isForUpdatePurpose = true
                                    onNavigate(NavigationItem.ADD_TODO.route)
                                }, onItemChecked = {})
                            }
                        }
                    }
                }else{
                    NoItemsAvailable()
                }
            }
        }
    }
}


@Composable
fun MakeTopCards(modifier: Modifier, text: String, image: Int, onClick:()-> Unit){
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .clickable { onClick() }) {

        Column(verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()) {
            Image(painter = painterResource(id = image),
                contentDescription = text,
                contentScale = ContentScale.Crop)

            Spacer(modifier = Modifier.padding(top = 12.dp))
            Text(text = text,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}




