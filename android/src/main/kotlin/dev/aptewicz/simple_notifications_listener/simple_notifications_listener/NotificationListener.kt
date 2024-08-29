package dev.aptewicz.simple_notifications_listener.simple_notifications_listener

import android.app.Notification
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification


class NotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(notification: StatusBarNotification) {
        // Retrieve package name to set as title.
        val packageName = notification.packageName
        // Retrieve extra object from notification to extract payload.
        val extras = notification.notification.extras

        // Pass data from one activity to another.
        val intent = Intent(NOTIFICATION_INTENT)
        intent.putExtra(NOTIFICATION_PACKAGE_NAME, packageName)

        if (extras != null) {
            val title = extras.getCharSequence(Notification.EXTRA_TITLE)
            val text = extras.getCharSequence(Notification.EXTRA_TEXT)

            intent.putExtra(NOTIFICATION_TITLE, title.toString())
            intent.putExtra(NOTIFICATION_MESSAGE, text.toString())
        }
        sendBroadcast(intent)
    }

    companion object {
        var NOTIFICATION_INTENT: String = "notification_event"
        var NOTIFICATION_PACKAGE_NAME: String = "notification_package_name"
        var NOTIFICATION_MESSAGE: String = "notification_message"
        var NOTIFICATION_TITLE: String = "notification_title"
    }
}
