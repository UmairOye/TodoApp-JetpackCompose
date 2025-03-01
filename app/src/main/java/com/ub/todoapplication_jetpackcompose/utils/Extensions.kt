package com.ub.todoapplication_jetpackcompose.utils

import androidx.navigation.NavController

fun NavController.navigateOnce(targetRoute: String) {
    if (this.currentDestination?.route !== targetRoute) {
        this.navigate(targetRoute)
    }
}
