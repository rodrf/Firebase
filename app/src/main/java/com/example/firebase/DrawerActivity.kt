package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.firebase.viewmodels.TODOViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_drawer.*

class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val navController : NavController by lazy{
        Navigation.findNavController(this, R.id.fragmentHost)
    }

    private val todoViewModel:TODOViewModel by lazy {
        ViewModelProviders.of(this).get(TODOViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        setUpNavigation()
        setUpObservers()

    }

    private fun setUpNavigation() {
        toolbarApp?.let { tb ->
            setSupportActionBar(tb)
            supportActionBar?.apply {
                setDisplayShowHomeEnabled(true)
                setDisplayHomeAsUpEnabled(true)
            }
            val toggle = ActionBarDrawerToggle(
                this@DrawerActivity,
                drawerLayout,
                tb,
                R.string.open_drawer,
                R.string.close_drawer
            )

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState() //Si llega a estar abierto se sincroniza
            drawerNavigationView.setNavigationItemSelectedListener(this)
        }
    }

    override fun onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }
    private fun setUpObservers(){
        todoViewModel.iscloseSessionUser.observe(this, Observer{ isCLoseSession ->
            if(isCLoseSession){
                finish()
            }
        })
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        drawerLayout.closeDrawers()
        when(item.itemId){
            R.id.action0 ->{
                navController.navigate(R.id.todoListFragment)
            }
            R.id.actionDoneList ->{
                navController.navigate(R.id.doneListFragment)
            }
            R.id.actionCloseSession ->{
                todoViewModel.closeSession()

            }
        }
        return  true
    }

}
