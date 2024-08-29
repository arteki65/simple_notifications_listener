package dev.aptewicz.simple_notifications_listener.simple_notifications_listener

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink


/** SimpleNotificationsListenerPlugin */
class SimpleNotificationsListenerPlugin: FlutterPlugin, EventChannel.StreamHandler  {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity

  companion object {
    const val TAG: String = "NotificationsPlugin"
  }

  private lateinit var eventChannel: EventChannel
  private var context: Context? = null

  private fun requestPermission() {
    /// Sort out permissions for notifications
    if (!permissionGranted()) {
      val permissionScreen = Intent(
        "android.settings" +
                ".ACTION_NOTIFICATION_LISTENER_SETTINGS"
      )
      permissionScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      context!!.startActivity(permissionScreen)
    }
  }

  private fun permissionGranted(): Boolean {
    val packageName = context!!.packageName
    val flat: String = Settings.Secure.getString(
      context!!.contentResolver,
      "enabled_notification_listeners"
    )
    if (!TextUtils.isEmpty(flat)) {
      val names = flat.split(":".toRegex()).dropLastWhile { it.isEmpty() }
        .toTypedArray()
      for (name in names) {
        val componentName = ComponentName.unflattenFromString(name)
        val nameMatch = TextUtils.equals(packageName, componentName!!.packageName)
        if (nameMatch) {
          return true
        }
      }
    }
    return false
  }

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPluginBinding) {
    /// Init event channel
    val binaryMessenger = flutterPluginBinding.binaryMessenger
    eventChannel = EventChannel(binaryMessenger, "notifications")
    eventChannel.setStreamHandler(this)

    /// Get context
    context = flutterPluginBinding.applicationContext
  }

  override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
    eventChannel.setStreamHandler(null)
  }

  override fun onListen(arguments: Any?, events: EventSink?) {
    if (permissionGranted()) {
      /// Set up receiver

      val intentFilter = IntentFilter()
      intentFilter.addAction(NotificationListener.NOTIFICATION_INTENT)

      val receiver = NotificationReceiver(events)
      context!!.registerReceiver(receiver, intentFilter)

      /// Set up listener intent
      val listenerIntent = Intent(context, NotificationListener::class.java)
      Log.d(TAG, "try to start notification listener service")
      context!!.startService(listenerIntent)
      Log.i(TAG, "Started the notification tracking service.")
    } else {
      requestPermission()
      Log.e(TAG, "Failed to start notification tracking; Permissions were not yet granted.")
    }
  }

  override fun onCancel(arguments: Any?) {
    Log.d(TAG, "on Cancel")
    eventChannel.setStreamHandler(null)
  }
}
