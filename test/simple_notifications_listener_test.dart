import 'package:flutter_test/flutter_test.dart';
import 'package:simple_notifications_listener/simple_notifications_listener.dart';
import 'package:simple_notifications_listener/simple_notifications_listener_platform_interface.dart';
import 'package:simple_notifications_listener/simple_notifications_listener_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

// class MockSimpleNotificationsListenerPlatform
//     with MockPlatformInterfaceMixin
//     implements SimpleNotificationsListenerPlatform {

//   @override
//   Future<String?> getPlatformVersion() => Future.value('42');
// }

// void main() {
//   final SimpleNotificationsListenerPlatform initialPlatform = SimpleNotificationsListenerPlatform.instance;

//   test('$MethodChannelSimpleNotificationsListener is the default instance', () {
//     expect(initialPlatform, isInstanceOf<MethodChannelSimpleNotificationsListener>());
//   });

//   test('getPlatformVersion', () async {
//     SimpleNotificationsListener simpleNotificationsListenerPlugin = SimpleNotificationsListener();
//     MockSimpleNotificationsListenerPlatform fakePlatform = MockSimpleNotificationsListenerPlatform();
//     SimpleNotificationsListenerPlatform.instance = fakePlatform;

//     expect(await simpleNotificationsListenerPlugin.getTestMessage(), '42');
//   });
// }
