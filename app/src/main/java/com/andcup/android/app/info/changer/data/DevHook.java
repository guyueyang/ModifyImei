/**
 * @author zhhaogen
 * ������ 2015-3-14 ����7:13:50
 */
package com.andcup.android.app.info.changer.data;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.HashMap;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 *
 *
 */
public class DevHook extends XC_MethodHook implements IXposedHookLoadPackage {

    public static final String prefs = "com.qioq.android.prefs";

    public static final String imei = "imei";
    public static final String model = "model";
    public static final String brand = "brand";
    public static final String imsi = "imsi";
    public static final String number = "number";
    public static final String simserial = "simserial";
    public static final String wifimac = "wifimac";
    public static final String wifissid="wifissid";
    public static final String wifibssid="wifibssid";
    public static final String bluemac = "bluemac";
    public static final String androidid = "androidid";
    public static final String serial = "serial";


    /**
     * ʹSharedPreferences
     *
     * @param
     */
    private void setData( ) {
        XSharedPreferences pre = new XSharedPreferences("com.andcup.android.app.info.changer", prefs);
        String ks[] = {imei, imsi,number, simserial,wifimac, bluemac, androidid, serial, brand, model,wifissid,wifibssid};
        //String ks[] = {imei, model, brand};
        HashMap<String, String> maps = new HashMap<String, String>();
        for (String k : ks) {
            String v = pre.getString(k, "111111111");
            maps.put(k, v);
        }
        if (!maps.isEmpty()) {
            HookAll(maps);
        }
    }

    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        setData();
    }

    private void HookAll(final HashMap<String, String> map) {
        try {
            HookMethod(TelephonyManager.class, "getDeviceId", map.get(imei));
            HookMethod(TelephonyManager.class, "getSubscriberId", map.get(imsi));
            HookMethod(TelephonyManager.class, "getLine1Number", map.get(number));
            HookMethod(TelephonyManager.class, "getSimSerialNumber", map.get(simserial));
            HookMethod(WifiInfo.class,         "getMacAddress", map.get(wifimac));
            HookMethod(BluetoothAdapter.class, "getAddress", map.get(bluemac));
            XposedHelpers.findField(Build.class, "SERIAL").set(null, map.get(serial));
            XposedHelpers.findField(Build.class, "MANUFACTURER").set(null, map.get(brand));
            XposedHelpers.findField(Build.class, "BRAND").set(null, map.get(brand));
            XposedHelpers.findField(Build.class, "MODEL").set(null, map.get(model));
            HookMethod(WifiInfo.class, "getSSID", map.get(wifissid));
            HookMethod(WifiInfo.class, "getBSSID", map.get(wifibssid));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod(
                    android.provider.Settings.Secure.class, "getString",
                    new Object[]{ContentResolver.class, String.class,
                            new XC_MethodHook() {
                                protected void afterHookedMethod(
                                        MethodHookParam param) throws Throwable {
                                    if (param.args[1] == "android_id") {
                                        param.setResult(map.get(androidid));
                                    }

                                }

                            }});
        } catch (Throwable e) {
        }
    }

    private void HookMethod(final Class cl, final String method,
                            final String result) {
        try {
            XposedHelpers.findAndHookMethod(cl, method,
                    new Object[]{new XC_MethodHook() {
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            param.setResult(result);
                        }

                    }});
        } catch (Throwable e) {
        }
    }
}
