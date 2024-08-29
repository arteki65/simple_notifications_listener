import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:simple_notifications_listener/notification.dart';

import 'simple_notifications_listener_method_channel.dart';

abstract class SimpleNotificationsListenerPlatform extends PlatformInterface {
  /// Constructs a SimpleNotificationsListenerPlatform.
  SimpleNotificationsListenerPlatform() : super(token: _token);

  static final Object _token = Object();

  static SimpleNotificationsListenerPlatform _instance =
      MethodChannelSimpleNotificationsListener();

  /// The default instance of [SimpleNotificationsListenerPlatform] to use.
  ///
  /// Defaults to [MethodChannelSimpleNotificationsListener].
  static SimpleNotificationsListenerPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SimpleNotificationsListenerPlatform] when
  /// they register themselves.
  static set instance(SimpleNotificationsListenerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Stream<NotificationEvent>? get notificationStream {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
