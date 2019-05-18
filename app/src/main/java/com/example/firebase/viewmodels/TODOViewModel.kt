package com.example.firebase.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.firebase.entities.ItemFirebaseTODO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TODOViewModel(application : Application): AndroidViewModel(application) {
    val iscloseSessionUser = MutableLiveData<Boolean>()

    private  val firebaseIntance: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }


    fun closeSession(){
        firebaseIntance.signOut()
        iscloseSessionUser.value = true
    }
    fun addItemTODO(title:String, description:String){
       /* val itemTODO = mutableMapOf<String, Any>()
        itemTODO["title"] = title
        itemTODO["descriptio"] = description
        itemTODO["isChecked"] = false*/

        val itemTODO = ItemFirebaseTODO(
            id = "asdasdasd",
            title= title,
            description = description,
            isChecked = false)

        db.collection("listRoomates")
            .document("mobileStudio")
            .collection("items")
            .add(itemTODO)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Log.v("Firestore", "Success write data")
                    Log.v("Firestore", "${task.result?.toString()}")
                }else{
                    Log.e("FirestoreFail", "Fail write data")
                    Log.e("FirestoreFail", "${task.exception?.message}")

                }

            }
    }

    fun getItemList() {
        db.collection("listRoomates")
            .document("mobileStudio")
            .collection("items")
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val data =task.result?.documents?.map { documentSnapshot ->
                        val document = documentSnapshot.toObject(ItemFirebaseTODO::class.java)
                        document?.id = documentSnapshot?.id
                        document

                    }
                    Log.v("LogData", "$data")
                }else{
                    task.exception?.printStackTrace()
                }
            }

    }
    fun updateDataItem(id:String, isChecked:Boolean){
        db.collection("listRoomates")
            .document("mobileStudio")
            .collection("items")
            .document(id)
            .update("checked", isChecked)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.v("UPDATE", "SUCCESS")
                    Log.v("UPDATE", "${task.result}")
                }
                else{
                    Log.e("UPDATE", "${task.exception?.printStackTrace()}")
                }
            }
    }

    fun deleteItem(id: String) {
        db
            .collection("listRoomates")
            .document("mobileStudio")
            .collection("items")
            .document(id)
            .delete()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.v("DELETE", "SUCCESS")
                    Log.v("DELETE", "${task.result}")
                }
                else{
                    Log.e("DELETE", "FAIL")
                }
            }
    }
    fun getItemListRealTime(){
        db
            .collection("listRoomates")
            .document("mobileStudio")
            .collection("items")
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null){
                    firebaseFirestoreException.printStackTrace()
                }
                else{
                    val documents = querySnapshot?.documents
                    val listItems = documents?.map { documentSnapshot ->
                        val itemTODO = documentSnapshot.toObject(ItemFirebaseTODO::class.java)
                        itemTODO?.id = documentSnapshot?.id
                        itemTODO

                    }
                    Log.e("Snapshot documents", "$listItems")
                }
            }
    }
    fun getItemRealTime(){
        db
            .collection("listRoomates")
            .document("mobileStudio")
            .collection("items")
            .document()
            .addSnapshotListener{documentSnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null){
                    firebaseFirestoreException.printStackTrace()
                }else{
                    val item = documentSnapshot?.toObject(ItemFirebaseTODO::class.java)
                    Log.e("DATA_SNAPSHOT", "${item}")
                }
            }
    }
}