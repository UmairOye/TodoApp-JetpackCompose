package com.ub.todoapplication_jetpackcompose.data.db.di

import android.content.Context
import androidx.room.Room
import com.ub.todoapplication_jetpackcompose.data.db.database.TodoDatabase
import com.ub.todoapplication_jetpackcompose.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, TodoDatabase::class.java, DATABASE_NAME).build()


    @Provides
    @Singleton
    fun provideTodoDao(database: TodoDatabase) = database.todoDao()



}