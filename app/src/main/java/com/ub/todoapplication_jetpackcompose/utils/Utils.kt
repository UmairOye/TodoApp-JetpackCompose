package com.ub.todoapplication_jetpackcompose.utils

import android.util.Log


object Utils {

    fun appLogCalls(tag: String = "TodoAppTag", message:String){
        Log.d(tag, message)
    }

}