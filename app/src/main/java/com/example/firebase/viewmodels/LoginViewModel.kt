package com.example.firebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.*

class LoginViewModel(application: Application): AndroidViewModel(application) {
    val isLogged = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<Throwable>()

    private val firebaseAuthInstance:FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun isUserLogged(){
        val currentUser = firebaseAuthInstance.currentUser
        isLogged.value = currentUser != null
    }
    fun registerUser(email: String, password: String){
        firebaseAuthInstance.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(it.isSuccessful)
                {
                    isLogged.value = true
                }else{
                    errorMessage.value  = when(val e = it.exception){
                        is FirebaseAuthUserCollisionException -> {
                            //Ya se registro
                            Throwable("Usuario ya registrado")
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            Throwable("La dirección de correo no es válida, intenta nuevamente")
                        }
                        else -> {
                            e?.printStackTrace()
                            //cualquier otro error
                            Throwable("Ocurrio un error inesperado, por favor intenta nuevamente")
                        }
                    }
                }
            }
    }

    fun loginUser(email: String, password: String){
        firebaseAuthInstance.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    isLogged.value = true
                }else{
                    task.exception?.printStackTrace()
                   errorMessage.value = when(val e = task.exception){
                        is FirebaseAuthEmailException -> {
                            Throwable("La dirección de correo o password no es válida, intenta nuevamente")
                        }
                       is FirebaseAuthInvalidCredentialsException -> {
                           Throwable("Las credenciales no son correctas, intenta nuevamente")
                       }
                       is FirebaseAuthInvalidUserException -> {
                           Throwable("Usuario no registrado, realiza el proceso de regitro")
                       }
                       else -> {
                           Throwable("Algo ocurrió, intenta nuevamente")
                       }
                    }
                }
            }
    }
}