package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.firebase.Utils.isValid
import com.example.firebase.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val modelViewLogin : LoginViewModel by lazy {
        ViewModelProviders.of(this@MainActivity).get(LoginViewModel::class.java)
    }
    private var constraintStatus =0
    private val c1 = ConstraintSet()
    private val c2 = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpObservers()
        setUpListeners()
        modelViewLogin.isUserLogged()
        c1.clone(constraintLogin)
        c2.clone(this, R.layout.activity_main_register)
    }


    private fun setUpListeners() {
//        btnLogin?.setOnClickListener{
//            modelViewLogin.registerUser("rodrigor@mail.com", "32323")
//        }
        //modelViewLogin.loginUser("rodrigo@mail.com", "1233453")
        btnLogin?.setOnClickListener{
            validateCredentials() // TODO: Hacer todo el proceso para tener un login bonito xd
        }
        btnRegister?.setOnClickListener{
            TransitionManager.beginDelayedTransition(constraintLogin)
            val constraintToAssign = c1
            constraintToAssign.applyTo(constraintLogin)
            constraintStatus = 0
        }

    }
    private  fun validateCredentials(){
        when(constraintStatus){
            0 -> {
                //val isValidMail = etEmail?.isValid("Por favor ingresa un correo", mi textInputLayout) ?: false
                //val isValidPassword = etPassword?.isValid("Por favor ingresa un passwprd", miTextInputLayout) ?: false
                val mail:String  = etEmail.text.toString()
                val pass: String = etPassword.text.toString()
                modelViewLogin.loginUser(mail, pass)
            }
        }
    }

    private fun setUpObservers() {
        modelViewLogin.isLogged.observe(this@MainActivity, Observer { isLogged ->
            Log.v("isLogged", "$isLogged")
            if(isLogged){
                //Finalizar la vista e iniciar la vista de firebase
                finish()
                startActivity(Intent(this@MainActivity, TODOActivity::class.java))
            }

        })
        modelViewLogin.errorMessage.observe(this@MainActivity, Observer { errorThrowable ->
            showSnackBarError(errorThrowable?.message)

        })
    }

    private fun showSnackBarError(message: String?) {
        message?.let {error ->
            Snackbar.make(constraintLogin, error, Snackbar.LENGTH_LONG).show()
        }

    }
}
