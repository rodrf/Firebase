package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.firebase.viewmodels.TODOViewModel
import kotlinx.android.synthetic.main.activity_todo.*

class TODOActivity : AppCompatActivity() {

    private val todoViewModel: TODOViewModel by lazy {
        ViewModelProviders.of(this).get(TODOViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)


        setUpObservables()
        setUpListeners()
        todoViewModel.getItemListRealTime()
        todoViewModel.getItemRealTime()
    }

    private fun setUpListeners() {
        imvExitApp?.setOnClickListener{
            todoViewModel.closeSession()
        }
        btnAdd?.setOnClickListener {
            todoViewModel.addItemTODO("Comprar tacos", "Comprar tacos de canasta")
        }
        btnGet?.setOnClickListener {
            todoViewModel.getItemList()
        }
        btnUpdate?.setOnClickListener {
            todoViewModel.updateDataItem("nmphmhm96dIA2oXM9Vmn", true)
        }
        btnDelete?.setOnClickListener {
            todoViewModel.deleteItem("cE9JSfT7w7vgTjXpMomH")
        }
    }

    private fun setUpObservables() {
        todoViewModel.iscloseSessionUser.observe(this, Observer { isCloseSession ->
            if (isCloseSession){
                finish()
                startActivity(Intent(this@TODOActivity, MainActivity::class.java))
            }
        })
    }
}
