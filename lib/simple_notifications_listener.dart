import 'package:simple_notifications_listener/notification.dart';

import 'simple_notifications_listener_platform_interface.dart';

class SimpleNotificationsListener {
  // Future<String?> getTestMessage() {
  //   return SimpleNotificationsListenerPlatform.instance.getPlatformVersion();
  // }
  Stream<NotificationEvent>? get notificationStream =>
      SimpleNotificationsListenerPlatform.instance.notificationStream;
}
