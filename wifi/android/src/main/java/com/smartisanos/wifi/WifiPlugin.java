package com.smartisanos.wifi;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** WifiPlugin */
public class WifiPlugin implements MethodCallHandler {

  private WifiManager mWifiManager;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
      final MethodChannel channel = new MethodChannel(registrar.messenger(), "plugins.flutter.io/wifi");
      channel.setMethodCallHandler(new WifiPlugin(registrar));
  }

  private WifiPlugin(Registrar registrar) {
      mWifiManager = (WifiManager) registrar.context().getSystemService(Context.WIFI_SERVICE);
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
      if (call.method.equals("getPlatformVersion")) {
          result.success("Android " + android.os.Build.VERSION.RELEASE);
      } else if (call.method.equals("getWifiIPAddress")) {
          getWifiIPAddress(result);
      } else if (call.method.equals("getWifiSsid")) {
          getWifiSsid(result);
      } else if (call.method.equals("getWifiMac")) {
          getWifiMac(result);
      } else if (call.method.equals("getNetworkId")) {
          getNetworkId(result);
      } else if (call.method.equals("getWifiInfo")) {
          getWifiInfo(result);
      } else {
          result.notImplemented();
      }
  }

  private void getWifiIPAddress(final Result result) {
      if (mWifiManager == null) {
          result.error("WifiManager is null!!!", null, null);
      }

      WifiInfo wifiInfo = mWifiManager.getConnectionInfo();

      int i_ip = 0;
      if (wifiInfo != null) {
        i_ip = wifiInfo.getIpAddress();
      }

      String ip = null;
      if (i_ip != 0)
          ip = String.format(
                      "%d.%d.%d.%d",
                      (i_ip & 0xff), (i_ip >> 8 & 0xff), (i_ip >> 16 & 0xff), (i_ip >> 24 & 0xff));

      result.success(ip);
  }

  private void getWifiSsid(final Result result) {
      if (mWifiManager == null) {
          result.error("WifiManager is null!!!", null, null);
      }

      String ssid = null;
      WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
      if (wifiInfo != null) {
          ssid = wifiInfo.getSSID();
      }

      result.success(ssid);
  }

  private void getWifiMac(final Result result) {
      if (mWifiManager == null) {
            result.error("WifiManager is null!!!", null, null);
        }

        String macAddr = null;
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            macAddr = wifiInfo.getMacAddress();
        }

        result.success(macAddr);
    }

    private void getNetworkId(final Result result) {
        if (mWifiManager == null) {
            result.error("WifiManager is null!!!", null, null);
        }

        int networkId = -1;
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            networkId = wifiInfo.getNetworkId();
        }

        result.success(networkId);
    }

    private void getWifiInfo(final Result result) {
        if (mWifiManager == null) {
            result.error("WifiManager is null!!!", null, null);
        }

        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();

        if (wifiInfo != null) {
            Map<String, Object> build = new HashMap<>();
            build.put("ssid", wifiInfo.getSSID());
            build.put("bssid", wifiInfo.getBSSID());
            build.put("mac", wifiInfo.getMacAddress());
            build.put("rssi", wifiInfo.getRssi());
            build.put("linkspeed", wifiInfo.getLinkSpeed());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                build.put("frequency", wifiInfo.getFrequency());
            }

            build.put("networkId", wifiInfo.getNetworkId());
            build.put("ip", wifiInfo.getIpAddress());

            result.success(build);
        } else {
            result.notImplemented();
        }

    }
}
