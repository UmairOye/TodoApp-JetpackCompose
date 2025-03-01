package com.ub.todoapplication_jetpackcompose.data.models

import androidx.compose.ui.graphics.Color

enum class Priorities(val color: Color) {
    LOW(Color.Gray),
    NORMAL(Color.Green),
    HIGH(Color.Yellow),
    URGENT(Color.Red),
    NONE(Color.LightGray)
}