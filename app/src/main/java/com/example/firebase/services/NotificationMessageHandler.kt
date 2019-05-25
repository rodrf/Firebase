package com.example.firebase.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.firebase.MainActivity
import com.example.firebase.R
import com.example.firebase.TODOActivity
import com.google.firebase.messaging.RemoteMessage

class NotificationMessageHandler(
    ctx: Context,
    remoteMessage: RemoteMessage)
{
    init {
        createNotification(ctx, remoteMessage)
    }

    fun createNotification(ctx: Context, remoteMessage: RemoteMessage){
        val dataNotification = remoteMessage.data

        val idBuy = dataNotification["idBuy"]
        val message = dataNotification["message"]
        val availableDisccount = dataNotification["availableDisccount"]
        val userName = dataNotification["userName"]

        val notificationIntent = Intent(ctx, MainActivity::class.java)
        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent = PendingIntent.getActivity(
            ctx,
            System.currentTimeMillis().toInt(),
            notificationIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notificationBuilder = NotificationCompat
            .Builder(ctx, "channelFirebase112").apply {
                setSmallIcon(R.drawable.ic_new_box)
                setLargeIcon(ctx.drawableToBitMap(R.drawable.ic_new_box))
                color = ContextCompat.getColor(ctx, R.color.colorAccent)
                setContentTitle("Mi titulo de notificacion")
                setContentText("$message de usuario $userName con el id de compra $idBuy")
                setContentIntent(pendingIntent)

            }
        val bigNotification = NotificationCompat.BigTextStyle(notificationBuilder)
            .bigText(message)
            .build()
        val notitificationManager =
            ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                "channelFirebase112",
                "channelFirebase112Name",
                NotificationManager.IMPORTANCE_HIGH)
            notitificationManager.createNotificationChannel(notificationChannel)
        }

        notitificationManager.notify(System.currentTimeMillis().toInt(), bigNotification)


    }
    fun Context.drawableToBitMap(@DrawableRes resource: Int): Bitmap {

        val drawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            this.resources.getDrawable(resource, this.theme)

        }else{
            this.resources.getDrawable(resource)
        }

        return if (drawable is BitmapDrawable){
            drawable.bitmap
        }else{
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0,0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }

    }

}
