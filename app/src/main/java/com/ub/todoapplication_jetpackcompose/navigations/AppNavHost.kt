package com.ub.todoapplication_jetpackcompose.navigations

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ub.todoapplication_jetpackcompose.ui.screens.ShowAddToScreen
import com.ub.todoapplication_jetpackcompose.ui.screens.ShowHomeScreen
import com.ub.todoapplication_jetpackcompose.ui.screens.ShowTodoList
import com.ub.todoapplication_jetpackcompose.ui.viewModels.TodoViewModel


@Composable
fun AppNavHost(viewModel: TodoViewModel){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route
    )

    {
        composable(
            route = NavigationItem.ADD_TODO.route) {
            ShowAddToScreen(
                onNavigate = {route -> navController.navigate(route)},
                onPressingBack = { navController.popBackStack() },
                viewModel = viewModel)
        }

        composable(route = NavigationItem.TODO_LIST.route) {
            ShowTodoList(
                onNavigate = {route -> navController.navigate(route)},
                onPressingBack = { navController.popBackStack()},
                viewModel = viewModel)
        }

        composable(route = NavigationItem.Home.route) {
            ShowHomeScreen(
                onNavigate = {route -> navController.navigate(route)},
                onPressingBack = { navController.popBackStack() },
                viewModel = viewModel)
        }
    }


}
