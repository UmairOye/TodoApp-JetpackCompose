package com.ub.todoapplication_jetpackcompose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_todo")
data class TodoModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priorities,
    var isChecked: Boolean = false
)
