package com.example.firebase.Utils

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

fun EditText.isValid(error: String?=null, inputLayout: TextInputLayout?=null):Boolean{
    return if (!this.text.isNullOrBlank()){
        true
    }else{
        error?.let {
            inputLayout?.let {
                it.error = error

            } ?: also {
                this.error = error
            }
        }
        false
    }
}