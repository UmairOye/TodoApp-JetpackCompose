package com.ub.todoapplication_jetpackcompose.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ub.todoapplication_jetpackcompose.data.db.dao.TodoDao
import com.ub.todoapplication_jetpackcompose.data.models.TodoModel

@Database(entities = [TodoModel::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase(){
    abstract fun todoDao(): TodoDao
}