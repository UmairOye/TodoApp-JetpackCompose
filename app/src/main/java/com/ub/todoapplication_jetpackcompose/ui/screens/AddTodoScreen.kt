package com.ub.todoapplication_jetpackcompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ub.todoapplication_jetpackcompose.R
import com.ub.todoapplication_jetpackcompose.ui.components.PriorityDropDown
import com.ub.todoapplication_jetpackcompose.data.models.Priorities
import com.ub.todoapplication_jetpackcompose.data.models.TodoModel
import com.ub.todoapplication_jetpackcompose.ui.components.TextWithDrawableLeft
import com.ub.todoapplication_jetpackcompose.ui.theme.BORDER_WIDTH
import com.ub.todoapplication_jetpackcompose.ui.theme.MEDIUM_PADDING
import com.ub.todoapplication_jetpackcompose.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import com.ub.todoapplication_jetpackcompose.ui.theme.SMALL_PADDING
import com.ub.todoapplication_jetpackcompose.ui.viewModels.TodoViewModel
import kotlinx.coroutines.launch

@Composable

fun ShowAddToScreen(onNavigate:(route: String)-> Unit,
                    onPressingBack:()->Unit,
                    viewModel: TodoViewModel) {

    var byDefaultHeadingText = ""
    var byDefaultDescriptionText = ""
    //used remember update state for avoid any necessary changed caused by configurations
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    val buttonText = if(viewModel.isForUpdatePurpose){
        stringResource(R.string.edit)
    }else{
        stringResource(R.string.save)
    }

    viewModel.updatedTodo?.let { updatedTodo->
        if(updatedTodo.title.isNotEmpty()){
            byDefaultHeadingText = updatedTodo.title
        }

        if(updatedTodo.description.isNotEmpty()){
            byDefaultDescriptionText = updatedTodo.description
        }

        viewModel.selectedPriority = updatedTodo.priority
    }

    var headingText by remember {
        mutableStateOf(byDefaultHeadingText)
    }

    var descriptionText by remember {
        mutableStateOf(byDefaultDescriptionText)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_START) {
//                Utils.appLogCalls(message = "onStartCalled")
////                currentOnStart()
//            } else if (event == Lifecycle.Event.ON_STOP) {
//                Utils.appLogCalls(message = "onStopCalled")
////                currentOnStop()
//            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            viewModel.isForUpdatePurpose = false
            viewModel.updatedTodo = null
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }



    /*focus manager and focus requester added due to when user type something in textFields and during typing he press anything else except textField, keyboard not gone,
    so we clear focus and make keyboard to disappear.
     */
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val prioritiesList = Priorities.entries.toTypedArray()
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    
    Scaffold(snackbarHost = {SnackbarHost(snackBarHostState) }) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .consumeWindowInsets(innerPadding)
        ) {

            TextWithDrawableLeft(
                text = stringResource(id = R.string.all_todos)
            ){onPressingBack()}




            TextField(
                value = headingText, onValueChange = {
                    headingText = it
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_heading),
                        style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MEDIUM_PADDING, end = MEDIUM_PADDING, top = 16.dp)
                    .height(PRIORITY_DROP_DOWN_HEIGHT)
                    .border(
                        width = BORDER_WIDTH, shape = RoundedCornerShape(SMALL_PADDING),
                        color = Color.LightGray
                    )
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )


            Spacer(modifier = Modifier.padding(top = MEDIUM_PADDING))
            PriorityDropDown( viewModel = viewModel, prioritiesList) { selectedPriority->
                viewModel.selectedPriority = selectedPriority
                focusManager.clearFocus()
            }

            Spacer(modifier = Modifier.padding(top = MEDIUM_PADDING))
            TextField(
                value = descriptionText, onValueChange = {
                    descriptionText = it
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_description),
                        style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = MEDIUM_PADDING)
                    .height(PRIORITY_DROP_DOWN_HEIGHT)
                    .border(
                        width = BORDER_WIDTH, shape = RoundedCornerShape(SMALL_PADDING),
                        color = Color.LightGray
                    ),


                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.padding(top = MEDIUM_PADDING))
            Button(
                onClick = {

                    focusManager.clearFocus()
                    if(headingText.isNotEmpty() && descriptionText.isNotEmpty()){
                        val todoModel = TodoModel(
                            id = 0,
                            title = headingText,
                            description =  descriptionText,
                            priority = viewModel.selectedPriority
                        )

                        if(viewModel.isForUpdatePurpose){
                            viewModel.updateTodo(viewModel.updatedTodo!!.id,
                                todoModel.title, todoModel.description,todoModel.priority,todoModel.isChecked)
                        }else {
                            viewModel.addTodo(todoModel)
                        }

                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(context.getString(R.string.todo_task_saved_successfully),
                                context.getString(
                                    R.string.done
                                ))
                        }
                        onPressingBack()
                    }else{
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                context.getString(R.string.all_fields_required), context.getString(
                                R.string.ok
                            ))
                        }
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MEDIUM_PADDING)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.blue)
                ),
                shape = RoundedCornerShape(SMALL_PADDING)


            ) {
                Text(
                    text = buttonText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }


}
