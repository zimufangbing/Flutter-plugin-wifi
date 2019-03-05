# wifi

This plugin allows Flutter apps to get some information about connected wifi.
This plugin works for Android only on version 0.0.1.

Sample usage to check current status:

```dart
import 'package:wifi/wifi.dart';

WifiInfo wifiInfo = await (wifi.getWifiInfo;
String ip = await wifi.getWifiIP
```
## Getting Started

This project is a starting point for a Flutter
[plug-in package](https://flutter.io/developing-packages/),
a specialized package that includes platform-specific implementation code for
Android and/or iOS.

For help getting started with Flutter, view our 
[online documentation](https://flutter.io/docs), which offers tutorials, 
samples, guidance on mobile development, and a full API reference.
