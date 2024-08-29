import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:simple_notifications_listener/notification.dart';

import 'simple_notifications_listener_platform_interface.dart';

/// An implementation of [SimpleNotificationsListenerPlatform] that uses method channels.
class MethodChannelSimpleNotificationsListener
    extends SimpleNotificationsListenerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('simple_notifications_listener');

  static const EventChannel _notificationEventChannel =
      EventChannel('notifications');

  Stream<NotificationEvent>? _notificationStream;

  @override
  Stream<NotificationEvent>? get notificationStream {
    // final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    // return version;
    if (Platform.isAndroid) {
      _notificationStream ??= _notificationEventChannel
          .receiveBroadcastStream()
          .map((event) => _notificationEvent(event));
      return _notificationStream;
    }

    throw NotificationException(
        'Notification API exclusively available on Android!');
  }

  NotificationEvent _notificationEvent(dynamic data) {
    return NotificationEvent.fromMap(data);
  }
}

/// Custom Exception for the plugin.
/// Thrown whenever the plugin is used on platforms other than Android.
class NotificationException implements Exception {
  String message;

  NotificationException(this.message);

  @override
  String toString() => '$runtimeType - $message';
}
