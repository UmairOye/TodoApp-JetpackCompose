package com.ub.todoapplication_jetpackcompose.navigations

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME_SCREEN.name)
    object ADD_TODO : NavigationItem(Screen.ADD_TODO_SCREEN.name)
    object TODO_LIST : NavigationItem(Screen.TODO_LIST_SCREEN.name)
}