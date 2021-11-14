package com.example.bookclub.utils

import android.content.Context
import android.widget.Toast

fun showError(context: Context, exception: Throwable){
    Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
}