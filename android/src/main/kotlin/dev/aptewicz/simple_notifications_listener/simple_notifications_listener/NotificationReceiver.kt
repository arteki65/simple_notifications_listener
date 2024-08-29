package dev.aptewicz.simple_notifications_listener.simple_notifications_listener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dev.aptewicz.simple_notifications_listener.simple_notifications_listener.SimpleNotificationsListenerPlugin.Companion.TAG
import io.flutter.plugin.common.EventChannel.EventSink


internal class NotificationReceiver(private val eventSink: EventSink?) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        /// Unpack intent contents
        val packageName = intent.getStringExtra(NotificationListener.NOTIFICATION_PACKAGE_NAME)
        val title = intent.getStringExtra(NotificationListener.NOTIFICATION_TITLE)
        val message = intent.getStringExtra(NotificationListener.NOTIFICATION_MESSAGE)

        /// Send data back via the Event Sink
        val data = HashMap<String, Any?>()
        data["packageName"] = packageName
        data["title"] = title
        data["message"] = message
        Log.d(TAG, "going to send notification: $data\n to event sink $eventSink")
        eventSink?.success(data)
    }
}
