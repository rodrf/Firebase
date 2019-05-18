package com.example.firebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddTODOFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todoform)
    }
    private fun cancelTODO(){
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
    private fun returnTODO(){
        val intent = Intent()
        intent.putExtra(TITLE_TODO_EXTRA, "TODO")
        intent.putExtra(DESCRIPTION_TODO_EXTRA, "TODO")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object{
        const val  TITLE_TODO_EXTRA = "TITLE_TODO_EXTRA"
        const val  DESCRIPTION_TODO_EXTRA = "TITLE_TODO_EXTRA"
        const val  ADD_TODO_EXTRA = "TITLE_TODO_EXTRA"
    }
}
