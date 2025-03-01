package com.ub.todoapplication_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ub.todoapplication_jetpackcompose.navigations.AppNavHost
import com.ub.todoapplication_jetpackcompose.ui.theme.TodoApplicationJetpackComposeTheme
import com.ub.todoapplication_jetpackcompose.ui.viewModels.TodoViewModel
import com.ub.todoapplication_jetpackcompose.utils.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val todoViewModel : TodoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        setContent {
            TodoApplicationJetpackComposeTheme {
                Box(
                    Modifier
                        .safeDrawingPadding()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    AppNavHost(viewModel = todoViewModel)
                }
            }
        }
    }

}