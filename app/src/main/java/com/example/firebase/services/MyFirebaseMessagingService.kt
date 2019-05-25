package com.example.firebase.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAGFb = "Firebase"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notificacion = remoteMessage.notification
        val title = notificacion?.title
        val body = notificacion?.body

        Log.d(TAGFb, "Title $title")
        Log.d(TAGFb, "Title $body")

        val dataNotification = remoteMessage.data

        val idBuy = dataNotification["idBuy"]
        val message = dataNotification["message"]
        val availableDisccount = dataNotification["availableDisccount"]
        val userName = dataNotification["userName"]

        Log.d(TAGFb, "idBuy $idBuy")
        Log.d(TAGFb, "message $message")
        Log.d(TAGFb, "availableDiscount $availableDisccount")
        Log.d(TAGFb, "userName $userName")

        NotificationMessageHandler(this, remoteMessage)


    }
}