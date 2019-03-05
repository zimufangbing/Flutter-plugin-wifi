import 'dart:async';
import 'dart:core';
import 'package:flutter/services.dart';

class Wifi {

  /// Constructs a singleton instance of [Wifi].
  ///
  /// [Wifi] is designed to work as a singleton.
  // When a second instance is created, the first instance will not be able to listen to the
  // EventChannel because it is overridden. Forcing the class to be a singleton class can prevent
  // misusage of creating a second instance from a programmer.
  factory Wifi() {
    if (_singleton == null) {
      _singleton = Wifi._();
    }
    return _singleton;
  }

  Wifi._();

  static Wifi _singleton;

  /// This information does not change from call to call. Cache it.
  WifiInfo _wifiInfo;

  static const MethodChannel _methodChannel =
      const MethodChannel('plugins.flutter.io/wifi');

  static Future<String> get platformVersion async {
    final String version = await _methodChannel.invokeMethod('getPlatformVersion');
    return version;
  }

  /// Obtains the IP address of the connected wifi network
  Future<String> get getWifiIP async {
    return await _methodChannel.invokeMethod('getWifiIPAddress');
  }

  /// Obtains the name of the connected wifi ssid
  Future<String> get getSsid async {
    return await _methodChannel.invokeMethod('getWifiSsid');
  }

  /// Obtains the mac address of local device
  Future<String> get getMacAddress async {
    return await _methodChannel.invokeMethod('getWifiMac');
  }

  /// Obtains the network Id of the configured network
  Future<int> get getNetworkId async {
    return await _methodChannel.invokeMethod('getNetworkId');
  }

  /// Obtains the WifiInfo of the configured network
  Future<WifiInfo> get getWifiInfo async => _wifiInfo ??=
      WifiInfo._fromMap(await _methodChannel.invokeMethod('getWifiInfo'));
}

/// Information derived from `android.net.wifi`.

class WifiInfo {
  WifiInfo._({
    this.ssid,
    this.bssid,
    this.mac,
    this.rssi,
    this.linkspeed,
    this.frequency,
    this.networkId,
    this.ip,
  });

  /// the ssid of configured network.
  final String ssid;

  /// the bssid of configured network.
  final String bssid;

  /// the mac of local device.
  final String mac;

  /// the rssi of configured network.
  final int rssi;

  /// the link speed of configured network.
  final int linkspeed;

  /// the frequency of configured network.
  final int frequency;

  /// the network id of configured network.
  final int networkId;

  /// the ip address of configured network.
  final int ip;

  /// Deserializes from the message received from [_kChannel].
  static WifiInfo _fromMap(dynamic message) {
    final Map<dynamic, dynamic> map = message;
    return WifiInfo._(
      ssid: map['ssid'],
      bssid: map['bssid'],
      mac: map['mac'],
      rssi: map['rssi'],
      linkspeed: map['linkspeed'],
      frequency: map['frequency'],
      networkId: map['networkId'],
      ip: map['ip'],
    );
  }

  @override
  String toString() {
    // TODO: implement toString
    StringBuffer builder = new StringBuffer(
        ['ssid : $ssid', 'bssid : $bssid', 'mac : $mac',
         'rssi : $rssi', 'linkspeed : $linkspeed', 'frequency : $frequency',
         'networkId : $networkId', 'ip : $ip']);
    return builder.toString();
  }
}
